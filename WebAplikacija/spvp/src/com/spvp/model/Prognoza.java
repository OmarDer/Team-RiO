/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.model;

import java.util.Date;

/**
 *
 * @author Ragib Smajic
 */
public class Prognoza {
    
    private Grad zaGrad;
    private String temperatura;
    private String vlaznostZraka;
    private String pritisakZraka;
    private String brzinaVjetra;
    private String vrijeme;
    private String weatherIcon;
    private Date datum;

    public Prognoza(Grad zaGrad, String temperatura, String vlaznostZraka, String pritisakZraka, String brzinaVjetra, String vrijeme) {
        this.zaGrad = zaGrad;
        this.temperatura = temperatura;
        this.vlaznostZraka = vlaznostZraka;
        this.pritisakZraka = pritisakZraka;
        this.brzinaVjetra = brzinaVjetra;
        this.vrijeme = vrijeme;
        this.weatherIcon = setweatherIcon(vrijeme);
    }

    public Prognoza(double temp, String vr, Grad city) {
        this.zaGrad = city;
        this.temperatura = String.valueOf(temp);
        this.vrijeme = vr;
        this.weatherIcon = setweatherIcon(vrijeme);
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
    
    
    public Grad getZaGrad() {
        return zaGrad;
    }

    public void setZaGrad(Grad zaGrad) {
        this.zaGrad = zaGrad;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getVlaznostZraka() {
        return vlaznostZraka;
    }

    public void setVlaznostZraka(String vlaznostZraka) {
        this.vlaznostZraka = vlaznostZraka;
    }

    public String getPritisakZraka() {
        return pritisakZraka;
    }

    public void setPritisakZraka(String pritisakZraka) {
        this.pritisakZraka = pritisakZraka;
    }

    public String getBrzinaVjetra() {
        return brzinaVjetra;
    }

    public void setBrzinaVjetra(String brzinaVjetra) {
        this.brzinaVjetra = brzinaVjetra;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
    
    private String setweatherIcon(String vrijeme){
        
        String vr = vrijeme.toLowerCase();

            if(vr.contains("sunny")){
                return "resources/images/sunny.jpg";
            }
            else if(vr.contains("rain")){
                return "resources/images/rain.jpg";
            }
            else if(vr.contains("snow")){
                return "resources/images/snow.jpg";
            }
            else if(vr.contains("cloudy") || vr.contains("overcast")){
                return "resources/images/clouds.jpg";
            }
            else if(vr.contains("light drizzle")){
                return "resources/images/rain.jpg";
            }
            else if(vr.contains("fog") || vr.contains("mist")){
                return "resources/images/fog.jpg";
            }
            return "";
    }
    
    
    

}