package com.hospital.controller.admin;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Appointment;
import com.hospital.model.Diagnosi;
import com.hospital.model.Illness;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.service.AppointmentService;
import com.hospital.service.DiagnosisService;
import com.hospital.service.IllnessService;

@Controller
@RequestMapping(value = "/admin/diagnosis")
public class DiagnosisController {

	@Autowired
	DiagnosisService service;
	@Autowired
	AppointmentService aservice;
	@Autowired
	IllnessService iservice;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("diagnosis/index");
		return mv;
	}
	
	@RequestMapping(value = "/getAllDianosis", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllDiagnosis() {
		List<Diagnosi> listDiagnosis = service.ListAllDianosis();
		return listDiagnosis;	
	}
	
	@RequestMapping(value = "/getDiagnosisByID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getDiagnosisByID(@PathVariable Integer id) {
		Diagnosi diag = service.GetDiagnosisByID(id);
		return diag;
	}
	
	@RequestMapping(value = "/getDiagnosisByAppID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getDiagnosisByAppID(@PathVariable Integer id) {
		List<Diagnosi> diags = service.ListAllDiagnosisByAppID(id);
		return diags;
	}
	
	@RequestMapping(value = "/getAllIllnessSuggestionByExaminationResult/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllIllnessSuggestionByExaminationResult(@PathVariable Integer id) {
		List<IllnessSuggestionDTO> illsug = service.ListAllSuggestIllnessByExaminationResult(id);
		return illsug;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView Create() {
		ModelAndView mv = new ModelAndView("diagnosis/diagnosis-form");
		List<Integer> appID = service.ListAllAppID();
		Diagnosi diag = new Diagnosi();	
		mv.addObject("diag",diag);
		mv.addObject("appID",appID);
		return mv;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView Update(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("diagnosis/diagnosis-form");
		List<Integer> appID = service.ListAllAppID();
		Diagnosi diag = new Diagnosi();	
		mv.addObject("diag",diag);
		mv.addObject("appID",appID);
		return mv;
	}
	
//	@RequestMapping(value = "/save/{appID}/{IllnessArray}", method = RequestMethod.POST)
//	public ModelAndView SaveData(@PathVariable Integer appID, Integer[] IllnessArray) {
//		try {
//			for (int i = 0; i < IllnessArray.length; i++) {
//				Diagnosi diag = new Diagnosi();
//				Appointment app = aservice.GetAppointmentByID(appID);				
//				diag.setAppointment(app);
//				Illness ill = iservice.GetIllnessByID(IllnessArray[i]);
//				diag.setIllness(ill);
//				diag.setDegree("neutral");
//				service.SaveData(diag);
//			}
//			
//		}catch(Exception ex){
//			throw ex;
//		}
//		return new ModelAndView("redirect:/admin/diagnosis/index");
//	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView SaveData(Diagnosi diagnosi) {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(stamp.getTime());
		diagnosi.setDianogsisTime(date);
		try {
			service.SaveData(diagnosi);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/procedure/profile/"+diagnosi.getAppointment().getAppId());
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object Delete(int id) {
		try {
			service.Delete(id);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
	
}
