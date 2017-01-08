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
public interface IWebService {

    ArrayList<Prognoza> getHistorijskePodatkeByLocation(Location lokacija, int brZadnjihDana) throws ParseException;

    Prognoza getWeatherByCityName(String city);

    Prognoza getWeatherByLocation(Location lokacija);
    
    Prognoza getHistorijskePodatkeByLocationOnSpecificDate(Location lokacija, Calendar cal) throws ParseException;
    
    String getWeatherByCityNameJSON(String city);
}
