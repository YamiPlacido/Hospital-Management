package com.hospital.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hospital.utils.WebUtils;

@Controller
//@Secured
@RequestMapping(value = "/admin")
public class MainController {

	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
		
		  User loginedUser = (User) ((Authentication) principal).getPrincipal();
		  
		  String userInfo = WebUtils.toString(loginedUser);
		  model.addAttribute("userInfo", userInfo);
		 
        model.addAttribute("title", "Index");
        return "admin_index";
    }
 
    @RequestMapping(value = "/IllnessType", method = RequestMethod.GET)
    public String illnessType(Model model, Principal principal) {
         
		/*
		 * User loginedUser = (User) ((Authentication) principal).getPrincipal();
		 * 
		 * String userInfo = WebUtils.toString(loginedUser);
		 * model.addAttribute("userInfo", userInfo);
		 */
    	//Set title
        model.addAttribute("title", "Illness Type");
        return "IllnessType";
    }
    
    @RequestMapping(value = "/illness", method = RequestMethod.GET)
    public String illness(Model model, Principal principal) {
        model.addAttribute("title", "Illness");
        return "illness/illness";
    }
    
    @RequestMapping(value = "/Users", method = RequestMethod.GET)
    public String users(Model model, Principal principal) {
        model.addAttribute("title", "Users");
        return "Users";
    }
    
    @RequestMapping(value = "/UserRole", method = RequestMethod.GET)
    public String userRole(Model model, Principal principal) {
        model.addAttribute("title", "User & Role");
        return "UserRole";
    }
    
    @RequestMapping(value = "/Roles", method = RequestMethod.GET)
    public String roles(Model model, Principal principal) {
        model.addAttribute("title", "Roles");
        return "Roles";
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
