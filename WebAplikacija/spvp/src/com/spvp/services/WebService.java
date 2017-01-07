/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.services;

import com.spvp.model.Location;
import com.spvp.model.Prognoza;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Ragib Smajic
 */
public class WebService implements IWebService{
    
    IWebService webService;

    public WebService(IWebService webService) {
        this.webService = webService;
    }

    @Override
    public ArrayList<Prognoza> getHistorijskePodatkeByLocation(Location lokacija, int brZadnjihDana) throws ParseException {
        return webService.getHistorijskePodatkeByLocation(lokacija, brZadnjihDana);
    }

    @Override
    public Prognoza getWeatherByCityName(String city) {
        return webService.getWeatherByCityName(city);
    }

    @Override
    public Prognoza getWeatherByLocation(Location lokacija) {
        return webService.getWeatherByLocation(lokacija);
    }

    @Override
    public Prognoza getHistorijskePodatkeByLocationOnSpecificDate(Location lokacija, Calendar cal) throws ParseException {
        return webService.getHistorijskePodatkeByLocationOnSpecificDate(lokacija, cal);
    }
    
}
