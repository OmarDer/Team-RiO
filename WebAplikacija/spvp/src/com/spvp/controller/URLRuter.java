package com.spvp.controller;

import com.spvp.services.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spvp.services.WebService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class URLRuter {
    
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView pocetna(HttpServletRequest request) {
		
		WebService weather = new WebService("http://api.worldweatheronline.com/premium/v1/weather.ashx", "c868e1f7b5a24e97a44211932160711");
		    
                return new ModelAndView("index", "prognoza", weather.getWeatherByLocation( LocationService.getClientLocation() ));
	}
        
        @RequestMapping(value="/", method = RequestMethod.POST)
	public ModelAndView lokacija(@RequestParam("city") String nazivGrada) {
		
		WebService weather = new WebService("http://api.worldweatheronline.com/premium/v1/weather.ashx", "c868e1f7b5a24e97a44211932160711");
                return new ModelAndView("index", "prognoza", weather.getWeatherByCityName(nazivGrada));
	}
        
        
        @RequestMapping(value="/proba", method = RequestMethod.GET)
	public ModelAndView proba(HttpServletRequest request) throws ParseException {
		
		WebService weather = new WebService("http://api.worldweatheronline.com/premium/v1/past-weather.ashx", "c868e1f7b5a24e97a44211932160711");
             
                return new ModelAndView("proba", "prognoza", weather.getHistorijskePodatkeByLocation(LocationService.getClientLocation(), 20));
	}

}
