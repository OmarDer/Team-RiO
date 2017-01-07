package com.spvp.services;

import com.spvp.model.Location;
import com.spvp.model.Prognoza;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class WorldWeatherOnlineWebService implements IWebService {

    private final String basicUrl;
    private final String historyUrl;
    private RestTemplate restTemplate;
    private final String apiKey;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        return restTemplate;
    }

    public WorldWeatherOnlineWebService() {
        this.basicUrl = "http://api.worldweatheronline.com/premium/v1/weather.ashx";
        this.historyUrl = "http://api.worldweatheronline.com/premium/v1/past-weather.ashx";
        this.apiKey = "67f8e93988414397bf2234724161212";
    }

    private Prognoza parsirajJSONObject(JSONObject json) {
        JSONObject podaci = json.getJSONObject("data");
        JSONObject nearestArea = podaci.getJSONArray("nearest_area").getJSONObject(0);
        JSONObject trenutnoStanje = podaci.getJSONArray("current_condition").getJSONObject(0);

        String gradName = nearestArea.getJSONArray("areaName").getJSONObject(0).getString("value");

        String temp = trenutnoStanje.getString("temp_C");
        String vlaznostZraka = trenutnoStanje.getString("humidity");
        String pritisak = trenutnoStanje.getString("pressure");
        String brzinaVjetra = trenutnoStanje.getString("windspeedKmph");
        String vrijeme = trenutnoStanje.getJSONArray("weatherDesc").getJSONObject(0).getString("value");

        Prognoza prog = new Prognoza(LocationService.getLocationByCityName(gradName).getGrad(), temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);

        return prog;
    }

    private ArrayList<Prognoza> parsirajJSONObjectUListu(JSONObject json, long brDana) throws ParseException {
        
        JSONObject podaci = json.getJSONObject("data");
        JSONObject nearestArea = podaci.getJSONArray("nearest_area").getJSONObject(0);
        JSONArray weatherArray = podaci.getJSONArray("weather");
        JSONObject trenutnoStanje;
        JSONObject vrijemeZaDan;

        String gradName = nearestArea.getJSONArray("areaName").getJSONObject(0).getString("value");

        String temp;
        String vlaznostZraka;
        String pritisak;
        String brzinaVjetra;
        String vrijeme;
        Date datum;

        Prognoza prog;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        ArrayList<Prognoza> lista = new ArrayList<>();
        
        if(brDana == 1) brDana--;

        for (int i = 0; i <= brDana; i++) {
            trenutnoStanje = weatherArray.getJSONObject(i);
            String dat = trenutnoStanje.getString("date");
            datum = format.parse(dat);

            vrijemeZaDan = trenutnoStanje.getJSONArray("hourly").getJSONObject(0);

            temp = vrijemeZaDan.getString("tempC");
            vlaznostZraka = vrijemeZaDan.getString("humidity");
            pritisak = vrijemeZaDan.getString("pressure");
            brzinaVjetra = vrijemeZaDan.getString("windspeedKmph");
            vrijeme = vrijemeZaDan.getJSONArray("weatherDesc").getJSONObject(0).getString("value");

            prog = new Prognoza(LocationService.getLocationByCityName(gradName).getGrad(), temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);

            prog.setDatum(datum);

            lista.add(prog);
        }

        return lista;
    }

    @Override
    public Prognoza getWeatherByCityName(String city) {
        String result = getRestTemplate().getForObject(this.basicUrl + "?q=" + city + ",ba&key=" + apiKey + "&fx=no&includelocation=yes&mca=no&format=json", String.class);
        return parsirajJSONObject(new JSONObject(result));
    }

    @Override
    public Prognoza getWeatherByLocation(Location lokacija) {
        String countryCode = lokacija.getCountryCode().toLowerCase();
        String result = getRestTemplate().getForObject(this.basicUrl + "?q=" + lokacija.getCity() + "," + countryCode + "&key=" + apiKey + "&fx=no&includelocation=yes&mca=no&format=json", String.class);
        return parsirajJSONObject(new JSONObject(result));
    }

    @Override
    public ArrayList<Prognoza> getHistorijskePodatkeByLocation(Location lokacija, int brZadnjihDana) throws ParseException {
        
        String a = lokacija.getCity();
        Date danasnji = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(danasnji);
        
        if(cal.get(Calendar.HOUR_OF_DAY) < 1)
            cal.add(Calendar.DATE, -1);
        
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        
        Date end = cal.getTime();
        brZadnjihDana--;
        cal.add(Calendar.DATE, -1 * brZadnjihDana);

        ArrayList<Date> pocetniDatumi = new ArrayList<>();
        ArrayList<Date> krajnjiDatumi = new ArrayList<>();
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);

        pocetniDatumi.add(cal.getTime());

        for (int i = 0;; i++) {

            if (cal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH)) {
                krajnjiDatumi.add(endCal.getTime());
                break;
            }
            
            YearMonth yearMonthObject = YearMonth.of(cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH) + 1);
            cal.set(Calendar.DATE, yearMonthObject.lengthOfMonth());
           

            krajnjiDatumi.add(cal.getTime());
            
            cal.add(Calendar.DATE, 1);

            pocetniDatumi.add(cal.getTime());

        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Prognoza> vracena;
        ArrayList<Prognoza> konacnaLista = new ArrayList<>();

        for (int i = 0; i < pocetniDatumi.size(); i++) {

            String startDate = df.format(pocetniDatumi.get(i));
            String endDate = df.format(krajnjiDatumi.get(i));

            long diff = krajnjiDatumi.get(i).getTime() - pocetniDatumi.get(i).getTime();
            long brDana = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            //System.out.println(this.historyUrl + "?q=" + lokacija.getCity() + ",ba&key=" + apiKey + "&date=" + startDate + "&enddate=" + endDate + "&tp=24&includelocation=yes&format=json");
            String result = getRestTemplate().getForObject(this.historyUrl + "?q=" + lokacija.getCity() + ",ba&key=" + apiKey + "&date=" + startDate + "&enddate=" + endDate + "&tp=24&includelocation=yes&format=json", String.class);

            vracena = parsirajJSONObjectUListu(new JSONObject(result), brDana);

            konacnaLista.addAll(vracena);
        }

        return konacnaLista;

    }

    @Override
    public Prognoza getHistorijskePodatkeByLocationOnSpecificDate(Location lokacija, Calendar cal) throws ParseException {
        
        String a = lokacija.getCity();
        //System.out.println("Ime grada: " + a);
        
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Prognoza> vracena;

        String date = df.format(cal.getTime());
            
        //System.out.println(this.historyUrl + "?q=" + lokacija.getCity() + ",ba&key=" + apiKey + "&date=" + date + "&enddate=" + date + "&tp=24&includelocation=yes&format=json");
        String result = getRestTemplate().getForObject(this.historyUrl + "?q=" + lokacija.getCity() + ",ba&key=" + apiKey + "&date=" + date + "&enddate=" + date + "&tp=24&includelocation=yes&format=json", String.class);
        
        vracena = parsirajJSONObjectUListu(new JSONObject(result), 1);
        
        return vracena.get(0);

    }
}
