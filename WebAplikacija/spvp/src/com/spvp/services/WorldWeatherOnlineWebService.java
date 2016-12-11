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

    private String basicUrl;
    private String historyUrl;
    private RestTemplate restTemplate;
    private String apiKey;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        return restTemplate;
    }

    public WorldWeatherOnlineWebService() {
        this.basicUrl = "http://api.worldweatheronline.com/premium/v1/weather.ashx";
        this.historyUrl = "http://api.worldweatheronline.com/premium/v1/past-weather.ashx";
        this.apiKey = "c868e1f7b5a24e97a44211932160711";
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

        Prognoza prog = new Prognoza(gradName, temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);

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

            prog = new Prognoza(gradName, temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);

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
        
        Date danasnji = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(danasnji);
        cal.add(Calendar.DATE, -1 * brZadnjihDana);

        Date start = cal.getTime();

        //long x = 24 * 3600 * 1000;
        Date end = danasnji;
        //x = brZadnjihDana * 24 * 3600 * 1000;
        //Date start = new Date(end.getTime() - x );

        System.out.println("Pocetni datum: " + start.toString());
        System.out.println("Krajnji datum: " + end.toString());

        ArrayList<Date> pocetniDatumi = new ArrayList<>();
        ArrayList<Date> krajnjiDatumi = new ArrayList<>();

        Date temp;
        Date noviPocetni;

        pocetniDatumi.add(start);

        for (int i = 0;; i++) {

            if (pocetniDatumi.get(i).getMonth() == end.getMonth()) {
                krajnjiDatumi.add(end);

                for (int j = 0; j < pocetniDatumi.size(); j++) {
                    System.out.println(pocetniDatumi.get(j).toString());
                    System.out.println(krajnjiDatumi.get(j).toString());

                }

                break;
            }

            //System.out.println(pocetniDatumi.get(i).toString());
            //System.out.println(pocetniDatumi.get(i).getYear() + 1900);
            //System.out.println(pocetniDatumi.get(i).getMonth());
            //System.out.println(pocetniDatumi.get(i).getDate());
            YearMonth yearMonthObject = YearMonth.of(pocetniDatumi.get(i).getYear() + 1900, pocetniDatumi.get(i).getMonth() + 1);
            //int daysInMonth = yearMonthObject.lengthOfMonth();

            temp = new Date();

            temp.setMonth(pocetniDatumi.get(i).getMonth());
            temp.setYear(pocetniDatumi.get(i).getYear());
            temp.setDate(yearMonthObject.lengthOfMonth());

            krajnjiDatumi.add(temp);

            noviPocetni = new Date();

            noviPocetni.setMonth(temp.getMonth() + 1);
            noviPocetni.setYear(temp.getYear());
            noviPocetni.setDate(1);

            pocetniDatumi.add(noviPocetni);

        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Prognoza> vracena;
        ArrayList<Prognoza> konacnaLista = new ArrayList<>();

        for (int i = 0; i < pocetniDatumi.size(); i++) {

            String startDate = df.format(pocetniDatumi.get(i));
            String endDate = df.format(krajnjiDatumi.get(i));

            long diff = krajnjiDatumi.get(i).getTime() - pocetniDatumi.get(i).getTime();
            long brDana = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            String countryCode = lokacija.getCountryCode().toLowerCase();
            //System.out.print(this.basicUrl + "?q=" + lokacija.getCity() + "," + countryCode + "&key=" + apiKey + "&date=" + startDate + "&enddate=" + endDate + "&tp=24&includelocation=yes&format=json");
            String result = getRestTemplate().getForObject(this.historyUrl + "?q=" + lokacija.getCity() + "," + countryCode + "&key=" + apiKey + "&date=" + startDate + "&enddate=" + endDate + "&tp=24&includelocation=yes&format=json", String.class);

            vracena = parsirajJSONObjectUListu(new JSONObject(result), brDana);

            konacnaLista.addAll(vracena);
        }

        return konacnaLista;

    }

}
