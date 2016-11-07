package com.spvp.services;

import com.spvp.model.Location;
import com.spvp.model.Prognoza;
import java.util.Date;
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
            
            return new Prognoza(gradName ,temp, vlaznostZraka, pritisak, brzinaVjetra, vrijeme);         
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
}
