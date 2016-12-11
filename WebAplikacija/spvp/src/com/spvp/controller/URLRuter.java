package com.spvp.controller;

import com.spvp.network.NNetwork;
import com.spvp.services.ApixuWebService;
import com.spvp.services.LocationService;
import com.spvp.services.WebService;
import com.spvp.services.WorldWeatherOnlineWebService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class URLRuter {
    
        WebService webService = new WebService(new WorldWeatherOnlineWebService());
        NNetwork nn = new NNetwork(new WorldWeatherOnlineWebService());
    
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView pocetna(HttpServletRequest request) {
				    
                return new ModelAndView("index", "prognoza", webService.getWeatherByLocation( LocationService.getClientLocation() ));
	}
        
        @RequestMapping(value="/", method = RequestMethod.POST)
	public ModelAndView lokacija(@RequestParam("city") String nazivGrada) {
		
                return new ModelAndView("index", "prognoza", webService.getWeatherByCityName(nazivGrada));
	}
        
        
        @RequestMapping(value="/proba/{brdana}", method = RequestMethod.GET)
	public ModelAndView proba(@PathVariable("brdana") String brDana, HttpServletRequest request) throws ParseException {
            
                return new ModelAndView("proba", "prognoza", webService.getHistorijskePodatkeByLocation(LocationService.getClientLocation(), Integer.parseInt(brDana)));
	}
        
        @RequestMapping(value="/narednatridana", method = RequestMethod.GET)
	public ModelAndView prognozaNarednaTriDana(HttpServletRequest request) throws ParseException {
            
                return new ModelAndView("narednatridana", "prognoza", nn.weatherForecast(12));
	}
        
        @RequestMapping(value="/prognozatridana/{grad}", method = RequestMethod.GET)
	public ModelAndView prognozaTriDana(@PathVariable("grad") String city, HttpServletRequest request) throws ParseException {
            
                return new ModelAndView("prognozatridana", "json", nn.weatherForecastByCityJSON(12, city));
	}

}
