//package com.hospital.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.hospital.service.*;
//import com.hospital.model.*;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.http.MediaType;
//
//@Controller
//@RequestMapping(value = "/appointment")
//
//public class InvoiceController {
//
//	@Autowired
//	AppointmentService appointmentService;
//	@Autowired
//	PatientService patientService;
//	@Autowired
//	DoctorService doctorService;
//
// 
//
//	@RequestMapping(value = "/appointment", method = RequestMethod.GET)
//	public ModelAndView appointment() {
//		ModelAndView model = new ModelAndView();
//		model.setViewName("appointment-list2");
//
//		return model;
//	}
//
//	@RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
//
//	public @ResponseBody Object list() {
//		List<Appointment> appointmentList = appointmentService.getAllAppointment();
//
//		return appointmentList;
//	}
//
//	 
//
//	@RequestMapping(value = "/addAppointment/", method = RequestMethod.GET)
//	public ModelAndView addArticle() {
//		ModelAndView model = new ModelAndView();
//
//		Appointment appointment = new Appointment();
//		List<Patient> patientList = patientService.getAllPatient();
//		model.addObject("patientList", patientList);
//		List<Doctor> doctorList = doctorService.getAllDoctor();
//		model.addObject("doctorList", doctorList);
//		model.addObject("appointmentForm", appointment);
//		model.setViewName("appointment-form");
//
//		return model;
//	}
//
//	@RequestMapping(value = "/update/{appId}", method = RequestMethod.GET)
//	public ModelAndView editArticle(@PathVariable long appId) {
//		ModelAndView model = new ModelAndView();
//
//		Appointment appointment = appointmentService.getAppointmentById(appId);
//		List<Patient> patientList = patientService.getAllPatient();
//		model.addObject("patientList", patientList);
//		List<Doctor> doctorList = doctorService.getAllDoctor();
//		model.addObject("doctorList", doctorList);
//		model.addObject("appointmentForm", appointment);
//		model.setViewName("appointment-form");
//
//		return model;
//	}
//
//	// validate trong controller
//	// up image (NewsController trong SpringJpaCrud)
//	@RequestMapping(value = "/saveAppointment", method = RequestMethod.POST)
//	public ModelAndView save(@ModelAttribute("appointmentForm") Appointment appointment) {
//		appointmentService.saveOrUpdate(appointment);
//
//		return new ModelAndView("redirect:/appointment/appointment");
//	}
//
// 
//	@RequestMapping(value="/delete", method = RequestMethod.POST)
//	@ResponseBody
//	public Object Delete(long appId) {
//		try {
//			appointmentService.deleteAppointment(appId);;
//		}catch(Exception ex) {
//			throw ex;
//		}
//		return null;
//	}
//	
//
//	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
//	public ModelAndView calendar() {
//		ModelAndView model = new ModelAndView("calendar3");
//		return model;
//	}
//	
//	@RequestMapping(value = "/saveCalendar", method = RequestMethod.POST)
//	public ModelAndView saveCalendar(Appointment appointment) {
//		appointmentService.saveOrUpdate(appointment);
//
//		return new ModelAndView("redirect:/appointment/calendar");
//	}
//}
