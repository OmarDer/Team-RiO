/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.services;

import com.spvp.model.Location;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Ragib Smajic
 */
public class LocationService {
    
    public static Location getLocationForIP()
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
}
