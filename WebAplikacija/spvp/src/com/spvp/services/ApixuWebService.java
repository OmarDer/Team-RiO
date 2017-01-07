/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.services;

import com.spvp.model.Location;
import com.spvp.model.Prognoza;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Ragib Smajic
 */
public class ApixuWebService implements IWebService{
    
    private final String basicUrl;
    private final String historyUrl;
    private final String apiKey;
    private RestTemplate restTemplate;
 

    private RestTemplate getRestTemplate() {
        
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        return restTemplate;
    }

    public ApixuWebService() {
        this.basicUrl = "http://api.apixu.com/v1/current.json";
        this.historyUrl = "http://api.apixu.com/v1/history.json";
        this.apiKey = "5c178ad8fa9a4bacb63154040161112";
    }

    @Override
    public ArrayList<Prognoza> getHistorijskePodatkeByLocation(Location lokacija, int brZadnjihDana) throws ParseException {
        
        if(brZadnjihDana > 29)
            brZadnjihDana = 29;
        
        Date danasnji = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(danasnji);
        cal.add(Calendar.DATE, -1 * brZadnjihDana);

        Date start = cal.getTime();
        ArrayList<Prognoza> prognoze = new ArrayList<>();

        Date end = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        String datum;
        
        Prognoza prognoza;
        
        String result;
        
        while(start.getDate() - 2 != end.getDate() || start.getMonth() != end.getMonth() || start.getYear() != end.getYear()){
            
            datum = df.format(start);
            //http://api.apixu.com/v1/history.json?key=5c178ad8fa9a4bacb63154040161112&q=Sarajevo&dt=2016-12-01

            result = getRestTemplate().getForObject(this.historyUrl + "?key=" + this.apiKey + "&q=" + lokacija.getCity() + "&dt=" + datum, String.class);
            prognoza = parsirajJSONuPrognozu(new JSONObject(result));
            
            prognoze.add(prognoza);
            
            cal.setTime(start);
            cal.add(Calendar.DATE, 1);

            start = cal.getTime();
            
        }
        
        return prognoze;
        
        
    }

    @Override
    public Prognoza getWeatherByCityName(String city) {
        
        //http://api.apixu.com/v1/current.json?key=5c178ad8fa9a4bacb63154040161112&q=Sarajevo
        String result = getRestTemplate().getForObject(this.basicUrl + "?key=" + this.apiKey + "&q=" + city, String.class);
        try {
            return parsirajCurrentWeatherJSON(new JSONObject(result));
        } catch (ParseException ex) {
            Logger.getLogger(ApixuWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Prognoza getWeatherByLocation(Location lokacija) {
        String result = getRestTemplate().getForObject(this.basicUrl + "?key=" + this.apiKey + "&q=" + lokacija.getCity(), String.class);
        try {
            return parsirajCurrentWeatherJSON(new JSONObject(result));
        } catch (ParseException ex) {
            Logger.getLogger(ApixuWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Prognoza parsirajJSONuPrognozu(JSONObject jsonObject) throws ParseException {
        
        JSONObject lokacija = jsonObject.getJSONObject("location");
        JSONArray forecastDay = jsonObject.getJSONObject("forecast").getJSONArray("forecastday");
        JSONArray prognozaPoSatima = forecastDay.getJSONObject(0).getJSONArray("hour");
        
        JSONObject dan = forecastDay.getJSONObject(0).getJSONObject("day");
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        
        String zaGrad = lokacija.getString("name");
        Double brzinaVjetra = dan.getDouble("maxwind_kph");
        Double temperatura = dan.getDouble("avgtemp_c");
        String vrijeme = dan.getJSONObject("condition").getString("text");
        Double pritisak = 0.0;
        Double vlaznostZraka = 0.0;
        Date datum = format.parse(forecastDay.getJSONObject(0).getString("date"));
        
        JSONObject tempSat;
        
        for(int i=0; i< prognozaPoSatima.length(); i++){
            
            tempSat = prognozaPoSatima.getJSONObject(i);
            
            pritisak += tempSat.getDouble("pressure_mb");
            vlaznostZraka += tempSat.getDouble("humidity");
            
        }
        
        pritisak = pritisak / prognozaPoSatima.length();
        vlaznostZraka = vlaznostZraka / prognozaPoSatima.length();
        
        Prognoza prog = new Prognoza(LocationService.getLocationByCityName(zaGrad).getGrad(),  String.valueOf(temperatura),  String.valueOf(vlaznostZraka),  String.valueOf(pritisak),  String.valueOf(brzinaVjetra), vrijeme);
        
        prog.setDatum(datum);
        
        return prog;
        
        
    }

    private Prognoza parsirajCurrentWeatherJSON(JSONObject jsonObject) throws ParseException {
        
        JSONObject lokacija = jsonObject.getJSONObject("location");
        JSONObject current = jsonObject.getJSONObject("current");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String zaGrad = lokacija.getString("name");
        Double brzinaVjetra = current.getDouble("wind_kph");
        Double temperatura = current.getDouble("temp_c");
        String vrijeme = current.getJSONObject("condition").getString("text");
        Double pritisak = current.getDouble("pressure_mb");
        Double vlaznostZraka = current.getDouble("humidity");
        Date datum = format.parse(current.getString("last_updated"));
        
        Prognoza prog = new Prognoza(LocationService.getLocationByCityName(zaGrad).getGrad(),  String.valueOf(temperatura),  String.valueOf(vlaznostZraka),  String.valueOf(pritisak),  String.valueOf(brzinaVjetra), vrijeme);
        
        prog.setDatum(datum);
        
        return prog;
        
    }

    @Override
    public Prognoza getHistorijskePodatkeByLocationOnSpecificDate(Location lokacija, Calendar cal) throws ParseException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
