package com.hospital.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Illness;
import com.hospital.model.IllnessType;
import com.hospital.service.IllnessService;

@Controller
@RequestMapping(value = "/admin/illness/illness")
public class IllnessController {
	
	@Autowired
	IllnessService iservice;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("illness/illness-index");
		return mv;
	}
	
	@RequestMapping(value = "/getAllIllness", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllIllness() {
		List<Illness> listIllness = iservice.ListAllIllness();
		return listIllness;
	}
	
	@RequestMapping(value = "/getIllnessByID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getIllnessByID(@PathVariable Long id) {
		Illness illness = iservice.GetIllnessByID(id);
		return illness;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView Create() {
		ModelAndView mv = new ModelAndView("illness/illness-form");
		List<IllnessType> illnessType = iservice.ListAllIllnessType();
		Illness illness = new Illness();
		mv.addObject("illness",illness);
		mv.addObject("illnessType",illnessType);
		return mv;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView Update(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("illness/illness-form");
		List<IllnessType> illnessType = iservice.ListAllIllnessType();
		Illness illness = iservice.GetIllnessByID(id);
		mv.addObject("illness",illness);
		mv.addObject("illnessType",illnessType);
		return mv;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView SaveData(Illness illness) {
		try {
			iservice.SaveData(illness);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/illness/illness/index");
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object Delete(Long id) {
		try {
			iservice.Delete(id);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
}
