package com.spvp.WSDataAccess;


import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class WebService {
	
	private String url;
	
	public WebService(String url){
		this.url = url;
	}
	
	public JSONObject getJSONData()
	{
		RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(this.url, String.class);
	    
	    JSONObject json = new JSONObject(result);
	    
	    return json;
	}
	
	public String getIzvjestaj(){
		JSONObject json = getJSONData();
		
		String lokacija = json.getJSONObject("current_observation").getJSONObject("display_location").getString("full");
	    String temperatura = String.valueOf(json.getJSONObject("current_observation").getDouble("temp_c"));
	    String vrijeme = json.getJSONObject("current_observation").getString("weather");
	    String relativnaVlaznost = json.getJSONObject("current_observation").getString("relative_humidity");
	    String icon_url = json.getJSONObject("current_observation").getString("icon_url");
	    
	    String izvjestaj = "<p><img src="+icon_url+" /> Vrijeme trenutno za " + lokacija + " je " + vrijeme + 
	    		". Temperatura je " + temperatura + ". Relativna vlaznost zraka je " + relativnaVlaznost + "</p>";
	    
	    return izvjestaj;
	}

}
