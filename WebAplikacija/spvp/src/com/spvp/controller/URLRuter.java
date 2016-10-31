package com.spvp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spvp.WSDataAccess.WebService;


@Controller
public class URLRuter {
	
	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
 
		String message = "<h1>Uspio si..</h1><br><br><h2><a href='/spvp'>Klini ovdje da se vratis..</a></h2>";
		return new ModelAndView("welcome", "message", message);
	}
	
	@RequestMapping("/")
	public ModelAndView pocetna() {
		
		WebService weather = new WebService("http://api.wunderground.com/api/cd6ca5deecbc854a/conditions/q/BiH/Sarajevo.json");
		
		return new ModelAndView("index", "vrijeme", weather.getIzvjestaj());
	}

}
