package com.spvp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spvp.WSDataAccess.WebService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class URLRuter {
    
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView pocetna(HttpServletRequest request) {
		
		WebService weather = new WebService("http://api.openweathermap.org/data/2.5/weather", "99acd32c392816b9b7736224d8c98176");
		return new ModelAndView("index", "vrijeme", request.getRemoteAddr());
	}
        
        @RequestMapping(value="/", method = RequestMethod.POST)
	public ModelAndView lokacija(@RequestParam("city") String nazivGrada) {
		
		WebService weather = new WebService("http://api.openweathermap.org/data/2.5/weather", "99acd32c392816b9b7736224d8c98176");
                return new ModelAndView("index", "prognoza", weather.getWeatherByCityName(nazivGrada));
	}

}
