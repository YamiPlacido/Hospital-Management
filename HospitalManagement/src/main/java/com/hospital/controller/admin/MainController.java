package com.hospital.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class MainController {

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView("test");
		return mv;
	}
}
