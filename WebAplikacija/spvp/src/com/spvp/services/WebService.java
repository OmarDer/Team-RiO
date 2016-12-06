package com.spvp.services;

import com.spvp.model.Location;
import com.spvp.model.Prognoza;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class WebService {
	
	private String basicUrl;
        private RestTemplate restTemplate;
        private String apiKey;
        
        private RestTemplate getRestTemplate()
        {
            if (restTemplate == null)
            {
                restTemplate = new RestTemplate();
            }
            
            return restTemplate;
        }
	
	public WebService(String url, String apiKey){
		this.basicUrl = url;
                this.apiKey = apiKey;
	}
        
        private Prognoza parsirajJSONObject(JSONObject json)
        {
            JSONObject podaci = json.getJSONObject("data");
            JSONObject nearestArea = podaci.getJSONArray("nearest_area").getJSONObject(0);
            JSONObject trenutnoStanje = podaci.getJSONArray("current_condition").getJSONObject(0);
            
            String gradName = nearestArea.getJSONArray("areaName").getJSONObject(0).getString("value");
            
            String temp = trenutnoStanje.getString("temp_C");
            String vlaznostZraka = trenutnoStanje.getString("humidity");
            String pritisak = trenutnoStanje.getString("pressure");
            String brzinaVjetra = trenutnoStanje.getString("windspeedKmph");       
            String vrijeme = trenutnoStanje.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
            
            Prognoza prog = new Prognoza(gradName ,temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);
            
            return prog;
        }
	
        private ArrayList<Prognoza> parsirajJSONObjectUListu(JSONObject json, long brDana) throws ParseException
        {
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
            
            for(int i=0; i<=brDana; i++)
            {
                trenutnoStanje = weatherArray.getJSONObject(i);
                String dat = trenutnoStanje.getString("date");
                datum = format.parse(dat);
                
                vrijemeZaDan = trenutnoStanje.getJSONArray("hourly").getJSONObject(0);
                
                temp = vrijemeZaDan.getString("tempC");
                vlaznostZraka = vrijemeZaDan.getString("humidity");
                pritisak = vrijemeZaDan.getString("pressure");
                brzinaVjetra = vrijemeZaDan.getString("windspeedKmph");       
                vrijeme = vrijemeZaDan.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
                
                prog = new Prognoza(gradName ,temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);
                
                prog.setDatum(datum);
                
                lista.add(prog);
            }
            
            return lista;
        }
        public Prognoza getWeatherByCityName(String city)
        {
            String result = getRestTemplate().getForObject(this.basicUrl + "?q=" + city + ",ba&key=" + apiKey + "&fx=no&includelocation=yes&mca=no&format=json", String.class);
            return parsirajJSONObject(new JSONObject(result));
        }
        
        public Prognoza getWeatherByLocation(Location lokacija)
        {
            String countryCode = lokacija.getCountryCode().toLowerCase();
            String result = getRestTemplate().getForObject(this.basicUrl + "?q=" + lokacija.getCity() + ","+ countryCode +"&key=" + apiKey + "&fx=no&includelocation=yes&mca=no&format=json", String.class);
            return parsirajJSONObject(new JSONObject(result));
        }
        
        public ArrayList<Prognoza> getHistorijskePodatkeByLocation(Location lokacija, int brZadnjihDana) throws ParseException
        {
            Date danasnji = new Date();
            long x = 24 * 3600 * 1000;
            Date end = new Date(danasnji.getTime() - x );
            x = brZadnjihDana * 24 * 3600 * 1000;
            Date start = new Date(end.getTime() - x );
            
            if(start.getMonth() != end.getMonth()){
                start = new Date();
                start.setDate(1);
            }
            
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = df.format(start);
            String endDate = df.format(end);
            
            long diff = end.getTime() - start.getTime();
            long brDana = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            String countryCode = lokacija.getCountryCode().toLowerCase();
            String result = getRestTemplate().getForObject(this.basicUrl + "?q=" + lokacija.getCity() + ","+ countryCode +"&key=" + apiKey + "&date="+startDate+"&enddate="+endDate+"&tp=24&includelocation=yes&format=json", String.class);

            return parsirajJSONObjectUListu(new JSONObject(result), brDana);
        }
       
}
