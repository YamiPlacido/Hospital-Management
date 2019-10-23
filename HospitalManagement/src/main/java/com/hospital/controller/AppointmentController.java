package com.hospital.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.dto.AppointmentDto;
import com.hospital.dto.DoctorScheduleInforDto;
import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.dto.ShiftDto;
import com.hospital.model.Appointment;
import com.hospital.model.Employee;
import com.hospital.model.Patient;
import com.hospital.model.Shift;
import com.hospital.service.AppointmentService;
import com.hospital.service.EmployeeService;
import com.hospital.service.PatientService;
import com.hospital.service.ShiftService;

@Controller
@RequestMapping(value = "/admin")
public class AppointmentController {

	@Autowired
	AppointmentService appointmentService;
	@Autowired
	PatientService patientService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ShiftService shiftService;
	
	@RequestMapping(value = "/appointment/appointment", method = RequestMethod.GET)
	public ModelAndView appointment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("appointment/appointment-list");

		return model;
	}

	@RequestMapping(value = "/appointment/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object list() {
		List<AppointmentDto> appointmentList = appointmentService.getListAppointment(); 
		return appointmentList;
	}

	@RequestMapping(value = "/appointment/doctorSchedule", method = RequestMethod.GET)
	public ModelAndView doctorSchedule() {
		ModelAndView model = new ModelAndView();
		List<Employee> employeeList = employeeService.getAllEmployee();
		DoctorScheduleSearchDto searchDto = new DoctorScheduleSearchDto();
		model.addObject("searchDto", searchDto);
		model.addObject("employeeList", employeeList);
		model.setViewName("appointment/doctor-schedule");
		return model;
	}
	
	@PostMapping(value = "/appointment/doctorGetSchedule")
	public ModelAndView getDoctorSchedule(@ModelAttribute("appointmentForm") DoctorScheduleSearchDto doctorScheduleSearchDto) {
		ModelAndView model = new ModelAndView();
		DoctorScheduleInforDto infor = appointmentService.getDoctorSchedule(doctorScheduleSearchDto);
		model.addObject("infor", infor);
		model.setViewName("appointment/doctor-schedule-detail");
		return model;
	}
	
	
	@RequestMapping(value = "/appointment/addAppointment/", method = RequestMethod.GET)
	public ModelAndView addArticle() {
		ModelAndView model = new ModelAndView();
		Appointment appointment = new Appointment(); 
		List<Patient> patientList = patientService.getAllPatient();
		model.addObject("patientList", patientList);
		List<Employee> employeeList = employeeService.getAllEmployee();
		model.addObject("employeeList", employeeList);
		List<Shift> shiftList = shiftService.getAllShift();
		model.addObject("shiftList", shiftList);
		model.addObject("appointmentForm", appointment);
		model.setViewName("appointment/appointment-form");

		return model;
	}

//	@RequestMapping(value = "/appointment/update/{appId}", method = RequestMethod.GET)
//	public ModelAndView editArticle(@PathVariable long appId) {
//		ModelAndView model = new ModelAndView();
//
//		Appointment appointment = appointmentService.getAppointmentById(appId);
//		List<Patient> patientList = patientService.getAllPatient();
//		model.addObject("patientList", patientList);
//		List<Employee> employeeList = employeeService.getAllEmployee();
//		model.addObject("employeeList", employeeList);
//		model.addObject("appointmentForm", appointment);
//		model.setViewName("appointment/appointment-form");
//		return model;
//	}

	@ResponseBody
	@PostMapping(value = "/appointment/saveAppointment")
	public ModelAndView save(@ModelAttribute("appointmentForm") AppointmentDto appointment) {
		ModelAndView model = new ModelAndView();
        try { 
        	appointmentService.saveApp(appointment);
		} catch (Exception e) {
			e.printStackTrace();
		}
        model.setViewName("appointment/appointment-list");
        return model;
	}

	@ResponseBody
	@GetMapping(value = "/appointment/getShift")
	public Object getShift(@RequestParam("employeeId") Long id, @RequestParam("date") String date) {
		AppointmentDto appointment = new AppointmentDto();
		Date date1;
		ShiftDto shiftDto = new ShiftDto();
		List<Shift> lstSfhit = new ArrayList<>();
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			appointment.setEmployeeId(id);
			appointment.setDate(date1);
			lstSfhit = appointmentService.getListShift(appointment);
			shiftDto.setLstShift(lstSfhit);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return shiftDto;
	}
	
	
	@RequestMapping(value="/appointment/export/{appId}", method = RequestMethod.GET)
	public ModelAndView export(@PathVariable long appId) {
		ModelAndView model = new ModelAndView();
		try {
			appointmentService.exportSlip(appId);
		}catch(Exception ex) {
			throw ex;
		}
		model.setViewName("appointment/appointment-list");
        return model;
	}
	
	@RequestMapping(value="/appointment/disable", method = RequestMethod.POST)
	@ResponseBody
	public Object disable(Long appId) {
		try {
			appointmentService.disableAppointment(appId);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
}
