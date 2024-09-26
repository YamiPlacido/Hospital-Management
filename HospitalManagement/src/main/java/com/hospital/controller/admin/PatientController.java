package com.hospital.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Appointment;
import com.hospital.model.Diagnosi;
import com.hospital.model.Patient;
import com.hospital.model.dto.DiagnosiDTO;
import com.hospital.service.DiagnosisService;
import com.hospital.service.PatientService;

@Controller
@RequestMapping(value = "/admin/patient")
public class PatientController {

	@Autowired
	PatientService service;
	
	
	@RequestMapping(value = "/getAllPatient", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllPatient() {
		List<Patient> listPatient = service.ListAllPatient();
		return listPatient;	
	}
	
	@RequestMapping(value = "/getPatientDetailByID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getPatientDetailByID(@PathVariable Integer id) {
		Patient patient = service.GetPatientByID(id);
		return patient;
	}
	
	@RequestMapping(value = "/getAllPatientIllnessByPatientID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<DiagnosiDTO> getAllPatientIllnessByPatientID(@PathVariable Integer id) {
		List<DiagnosiDTO> listPatientIllness = service.ListAllPatientIllnessByID(id);
		return listPatientIllness;
	}
	
}
