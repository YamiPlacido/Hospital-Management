package com.hospital.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hospital.repository.UserRepository;
import com.hospital.utils.WebUtils;

@Controller
//@Secured
@RequestMapping(value = "/admin")
public class MainController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String adminIndex(Model model) {
    	
        return "dashboard";
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String adminLogin(Model model) {
    	
        return "adminLogin";
    }
 
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }
 
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
            String userInfo = WebUtils.toString(loginedUser);
 
            model.addAttribute("userInfo", userInfo);
 
			
			  String message = principal.getName()  +
			  "<br> You do not have permission to access this page!";
			  model.addAttribute("message", message);
			 
			 
 
        }
 
        return "403Page";
    }
	
}
