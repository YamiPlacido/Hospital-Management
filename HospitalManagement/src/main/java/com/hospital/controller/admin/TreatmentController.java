package com.hospital.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Treatment;
import com.hospital.service.TreatmentService;

@Controller
@RequestMapping(value = "/admin/treatment")
public class TreatmentController {

	@Autowired
	TreatmentService service;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("treatment/treatment-index");
		return mv;
	}
	
	@RequestMapping(value = "/getAllTreatment",method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllTreatment() {
		List<Treatment> listTm = service.ListAllTreatment();
		return listTm;
	}
	
	@RequestMapping(value = "/getAllTreatmentByAppID/{appID}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllTreatmentByAppID(@PathVariable Long appID) {
		List<Treatment> listTm = service.ListAllTreatmentByAppID(appID);
		return listTm;
	}
	
	@RequestMapping(value = "/getTreatmentByID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getTreatmentByID(@PathVariable Long id) {
		Treatment tm = service.GetTreatmentByID(id);
		return tm;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView Create() {
		ModelAndView mv = new ModelAndView("treatment/treatment-form");
		List<Integer> illnessID = service.ListAllIllnessID();
		Treatment tm = new Treatment();
		mv.addObject("tmm",tm);
		mv.addObject("illnessID",illnessID);
		return mv;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView Update(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("treatment/treatment-form");
		List<Integer> illnessID = service.ListAllIllnessID();
		Treatment tm = new Treatment();
		mv.addObject("tm",tm);
		mv.addObject("illnessID",illnessID);
		return mv;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView SaveData(Treatment tm) {
		try {
			service.SaveData(tm);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/treatment/treatment-index");
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object Delete(Long id) {
		try {
			service.Delete(id);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
}
