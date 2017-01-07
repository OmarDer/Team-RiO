/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.services;

import com.spvp.model.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Ragib Smajic
 */
public class LocationService {
    
    public static Location getClientLocation()
    {
       RestTemplate restTemplate = new RestTemplate();
       String result = restTemplate.getForObject("http://ip-api.com/json", String.class);
       
       JSONObject json = new JSONObject(result);
          
       if(json.getString("status").equals("success"))
       {
           Location lokacija = new Location(json.getString("country"), json.getString("city"), json.getDouble("lat"), json.getDouble("lon"), true, json.getString("countryCode"));
           
           return lokacija;
       }
       else
       {
           return new Location(false);
       }
    }
    
    public static Location getLocationByCityName(String cityName){
        
       RestTemplate restTemplate = new RestTemplate();
        
       String result = restTemplate.getForObject("https://maps.google.com/maps/api/geocode/json?address="+ cityName +"&sensor=false&region=ba&key=AIzaSyAjnQDa9sdWodJ98wH9VRV0E3efneFYv64", String.class);
       
       JSONObject json = new JSONObject(result);
        
       if(json.getString("status").equals("OK"))
       {
           JSONObject rezultat = json.getJSONArray("results").getJSONObject(0);
           JSONArray komponenteAdrese = rezultat.getJSONArray("address_components");
           
           String drzava = komponenteAdrese.getJSONObject(3).getString("long_name");
           String grad = komponenteAdrese.getJSONObject(0).getString("long_name");
           Double latitude = rezultat.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
           Double longitude = rezultat.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
           String kodDrzave = "ba";
           
           Location lokacija = new Location(drzava, grad, latitude, longitude, true, kodDrzave);
           
           return lokacija;
       }
       else
       {
           return new Location(false);
       }
        
    }
}
