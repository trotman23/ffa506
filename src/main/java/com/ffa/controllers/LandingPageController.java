package com.ffa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingPageController {

	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model){
		return "dashboard";
	}
}
