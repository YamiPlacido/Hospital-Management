package com.hospital.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.TreatmentMethodIllness;
import com.hospital.service.TreatmentMethodIllnessService;

@Controller
@RequestMapping(value = "/admin/treatmentmethodillness")
public class TreatmentMethodIllnessController {

	@Autowired
	TreatmentMethodIllnessService service;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("treatmentmethodillness/treatmentmethodillness-index");
		return mv;
	}
	
	@RequestMapping(value = "/getAllTreatmentMethodIllness", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllTreatmentMethodIllness() {
		List<TreatmentMethodIllness> listTmmi = service.ListAllTreatmentMethod();
		return listTmmi;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView Create() {
		ModelAndView mv = new ModelAndView("treatmentmethodillness/treatmentmethodillness-form");
		List<Long> illnessID = service.ListAllIllnessID();
		TreatmentMethodIllness tmmi = new TreatmentMethodIllness();
		mv.addObject("tmmi",tmmi);
		mv.addObject("illnessID",illnessID);
		return mv;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView Update(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("treatmentmethodillness/treatmentmethodillness-form");
		List<Long> illnessID = service.ListAllIllnessID();
		TreatmentMethodIllness tmmi = new TreatmentMethodIllness();
		mv.addObject("tmmi",tmmi);
		mv.addObject("illnessID",illnessID);
		return mv;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView SaveData(TreatmentMethodIllness tmmi) {
		try {
			service.SaveData(tmmi);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/treatmentmethodillness/treatmentmethodillness-index");
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
