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
        
       System.out.println("https://maps.google.com/maps/api/geocode/json?address="+ cityName +"&sensor=false&region=ba&key=AIzaSyAjnQDa9sdWodJ98wH9VRV0E3efneFYv64");
       
       String result = restTemplate.getForObject("https://maps.google.com/maps/api/geocode/json?address="+ cityName +"&sensor=false&region=ba&key=AIzaSyBkMlz8ut87ohCotSbU5Brja2tTsrIpM24", String.class);
       
       
       JSONObject json = new JSONObject(result);
        
    
        JSONObject rezultat = json.getJSONArray("results").getJSONObject(0);
        JSONArray komponenteAdrese = rezultat.getJSONArray("address_components");

        String drzava = komponenteAdrese.getJSONObject(komponenteAdrese.length() - 1).getString("long_name");
        String grad = komponenteAdrese.getJSONObject(0).getString("long_name");
        grad = grad.replace('è', 'c');
        grad = grad.replace("È", "c");
        grad = grad.replace('æ', 'c');
        grad = grad.replace("Æ", "c");
        grad = grad.replace("Š", "s");
        grad = grad.replace("š", "s");
        grad = grad.replace("ž", "z");
        grad = grad.replace("Ž", "z");

        Double latitude = rezultat.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        Double longitude = rezultat.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
        String kodDrzave = "ba";

        Location lokacija = new Location(drzava, grad, latitude, longitude, true, kodDrzave);

        return lokacija;
   
        
    }
}
