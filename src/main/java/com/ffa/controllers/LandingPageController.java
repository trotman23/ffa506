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
	
	@RequestMapping("/signin")
	public String signin(){
		return "signin";
	}
	
	@RequestMapping("/LandingPage")
	public String LandingPage(Model model){
		return "LandingPage";
	}
	
	@RequestMapping("/main")
	public String DashMain(Model model){
		return "main";
	}	
	@RequestMapping("/nav")
	public String Nav(){
		return "nav";
	}
}
