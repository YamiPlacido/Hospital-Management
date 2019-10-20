package com.hospital.controller.admin;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Illness;
import com.hospital.model.IllnessType;
import com.hospital.service.IllnessTypeService;

@Controller
@RequestMapping(value = "/admin/illness/category")
public class IllnessTypeController {
	
	@Autowired
	IllnessTypeService itservice;	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView IllnessCategoryIndex() {
		ModelAndView mv = new ModelAndView("illness/illness-category-index");
		try {
			List<IllnessType> illnessType = itservice.ListAllIllnessType();
			if(illnessType != null) {
				mv.addObject("listIllnessType",illnessType);
			}
		}catch(Exception ex) {
			throw ex;
		}
		return mv;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView Create() {
		ModelAndView mv = new ModelAndView("illness/illness-category-form");
		IllnessType illnessType = new IllnessType();
		mv.addObject("illnessType",illnessType);
		return mv;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView Update(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("illness/illness-category-form");
		IllnessType illnessType = itservice.GetIllnessTypeByID(id);
		mv.addObject("illnessType",illnessType);
		return mv;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView SaveData(@ModelAttribute("illness_type_form") IllnessType illnessType) {
		try {
			itservice.SaveData(illnessType);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/illness/category/index");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object Delete(@RequestParam long id) {
		try {
			itservice.Delete(id);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
	
}
