package com.hospital.controller.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Diagnosi;
import com.hospital.model.Illness;
import com.hospital.model.Treatment;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.model.dto.TreatmentSuggestionDTO;
import com.hospital.service.AppointmentService;
import com.hospital.service.DiagnosisService;
import com.hospital.service.IllnessService;
import com.hospital.service.TreatmentService;

@Controller
@RequestMapping(value = "/admin/procedure")
public class ProcedureController {
	
	@Autowired
	AppointmentService aservice;
	
	@Autowired
	DiagnosisService dservice;
	
	@Autowired
	IllnessService iservice;
	
	@Autowired
	TreatmentService tservice;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("procedure/procedure-index");
		return mv;
	}
	
	@RequestMapping(value = "/profile/{appID}", method = RequestMethod.GET)
	public ModelAndView DisplayIllnessProfile(@PathVariable Integer appID) {
		ModelAndView mv = new ModelAndView("procedure/procedure-profile");
		Integer patientID = aservice.GetPatientIDByAppID(appID);
		Diagnosi diag = new Diagnosi();
		Treatment treatment = new Treatment();
		List<IllnessSuggestionDTO> illsug = dservice.ListAllSuggestIllnessByExaminationResult(patientID);
		List<Illness> illall = iservice.ListAllIllness();
		List<TreatmentSuggestionDTO> treatsug = tservice.ListAllSuggestTreatmentByAppID(appID);
		List<Treatment> treatall = tservice.ListAllTreatmentByAppID(appID);
		mv.addObject("patientID",patientID);
		mv.addObject("appID",appID);
		mv.addObject("diag",diag);
		mv.addObject("illsug",illsug);
		mv.addObject("illall",illall);
		mv.addObject("treatment",treatment);
		mv.addObject("treatsug",treatsug);
		mv.addObject("treatall",treatall);
		return mv;
	}
}
