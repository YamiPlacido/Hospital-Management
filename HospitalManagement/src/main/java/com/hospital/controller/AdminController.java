//package com.hospital.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.web.bind.annotation.*;
//
//import com.hospital.model.*;
//import com.hospital.repository.*; 
////
//@RestController
//@RequestMapping(value = "/admin")
//public class AdminController {
//	@Autowired
//	AppointmentRepository appointmentRepository;
//	
//	@GetMapping("/appointment")
//	public List<Appointment> viewallApp() {
//		Page<Appointment> pages = appointmentRepository.findAll(PageRequest.of(0, 8));
//		List<Appointment> list = pages.getContent();
//		return list;
//	}
//	@GetMapping("/appointment/{id}")
//	public Optional<Appointment> viewAppDetail(@PathVariable int id) {
//		return appointmentRepository.findById(id);
//	}
//	
//	@PostMapping("/appointment")
//	Appointment newApp(@RequestBody Appointment newApp) {
//	    return appointmentRepository.save(newApp);
//	  }
//	
//	@RequestMapping(value = "/appointment", method = RequestMethod.PUT)
//	public void editDoctor() {
//		
//	}
//	 
//		
//	 
//}
