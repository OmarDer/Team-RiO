package com.spvp.WSDataAccess;


import com.spvp.model.Grad;
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
            String gradName = json.getString("name");
            String gradId = String.valueOf( json.getDouble("id") );
            String gradLat = String.valueOf( json.getJSONObject("coord").getDouble("lat") );
            String gradLon = String.valueOf( json.getJSONObject("coord").getDouble("lon") );
            
            Grad grad = new Grad(gradId, gradName, gradLat, gradLon);
            
            Double temp = json.getJSONObject("main").getDouble("temp");
            Double minTemp = json.getJSONObject("main").getDouble("temp_min");
            Double maxTemp = json.getJSONObject("main").getDouble("temp_max");
            Double vlaznostZraka = json.getJSONObject("main").getDouble("humidity");
            Double pritisak = json.getJSONObject("main").getDouble("pressure");
            Double brzinaVjetra = json.getJSONObject("wind").getDouble("speed");       
            Double vidljivost = json.getDouble("visibility");
            String vrijeme = (json.getJSONArray("weather").getJSONObject(0)).getString("main");
            
            Date zaDatum = new Date(json.getInt("dt"));

            String kodDrzave = json.getJSONObject("sys").getString("country");
            
            return new Prognoza(zaDatum, grad,temp, minTemp, maxTemp, vlaznostZraka, pritisak, brzinaVjetra, vidljivost, vrijeme, kodDrzave);
            
        }
	
        public Prognoza getWeatherByCityName(String city)
        {
            String result = getRestTemplate().getForObject(this.basicUrl + "?q=" + city + "&appid=" + apiKey, String.class);
            return parsirajJSONObject(new JSONObject(result));
        }
	
	//public String getIzvjestaj(){
		//JSONObject json = getJSONData();
		
		//String lokacija = json.getJSONObject("current_observation").getJSONObject("display_location").getString("full");
	   // String temperatura = String.valueOf(json.getJSONObject("current_observation").getDouble("temp_c"));
	   // String vrijeme = json.getJSONObject("current_observation").getString("weather");
	   // String relativnaVlaznost = json.getJSONObject("current_observation").getString("relative_humidity");
	  //  String icon_url = json.getJSONObject("current_observation").getString("icon_url");
	    
	  //  String izvjestaj = "<p><img src="+icon_url+" /> Vrijeme trenutno za " + lokacija + " je " + vrijeme + 
	  //  		". Temperatura je " + temperatura + ". Relativna vlaznost zraka je " + relativnaVlaznost + "</p>";
	    
	   // return izvjestaj;
	//}

}
