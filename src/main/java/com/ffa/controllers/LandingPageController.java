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
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model){
		return "dashboard";
	}
	
	@RequestMapping("/dashboard2")
	public String dashboard2(Model model){
		return "dashboard2";
	}
	
	@RequestMapping("/LandingPage")
	public String LandingPage(Model model){
		return "LandingPage";
	}
	
	@RequestMapping("/login")
	public String login(Model model){
		return "login";
	}
}
