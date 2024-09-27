package com.hospital.controller.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Appointment;
import com.hospital.model.Illness;
import com.hospital.service.AppointmentService;

import freemarker.template.utility.ObjectFactory;

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
	
	@RequestMapping(value = "/RejectProfile/{appID}", method = RequestMethod.GET)
	public ModelAndView RejectProfile(@PathVariable Long appID, HttpSession session) {
		try {
			Appointment app = service.getAppointmentById(appID);
			app.setStage("REJECTED");
			String modifier = session.getAttribute("usersessionname").toString();
			app.setModifiedBy(modifier);
			Timestamp stamp = new Timestamp(System.currentTimeMillis());
			Date date = new Date(stamp.getTime());
			app.setModifiedDate(date);
			service.SaveData(app);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/procedure/index");
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
