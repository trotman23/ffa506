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
	
	@RequestMapping("/fantasy-news")
	public String fantasyNews(){
		return "fantasy-news";
	}
	
	@RequestMapping("/help")
	public String help(){
		return "help";
	}
	
	@RequestMapping("/request-feature")
	public String requestFeature(){
		return "request-feature";
	}
	
	@RequestMapping("/report-bug")
	public String reportBug(){
		return "report-bug";
	}
	
	@RequestMapping("/top-performers")
	public String topPerformers(){
		return "top-performers";
	}
	
	
}
