package com.hospital.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;

@Controller
@RequestMapping(value = "/admin/appointment")
public class AppointmentHyController {
	
	@Autowired
	AppointmentService service;	
	
	@RequestMapping(value = "/getAllAppointment", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllAppointment() {
		List<Appointment> listApp = service.ListAllAppointment();
		return listApp;
	}
	
	@RequestMapping(value = "/getAllAppointmentByDoctorID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllAppointmentByDoctorID(@PathVariable Long id) {
		List<Appointment> listApp = service.ListAllAppointmentByDoctorID(id);
		return listApp;
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
