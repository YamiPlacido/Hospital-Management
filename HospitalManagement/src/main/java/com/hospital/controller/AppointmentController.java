package com.hospital.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.hospital.model.Employee;
import com.hospital.model.Patient;
import com.hospital.model.Shift;
import com.hospital.repository.EmployeeRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;
import com.hospital.service.AppointmentService;
import com.hospital.service.EmployeeService;
import com.hospital.service.MailService;
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
	@Autowired
	UserRepository userRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	MailService mailService;

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
	public ModelAndView getDoctorSchedule(
			@ModelAttribute("appointmentForm") DoctorScheduleSearchDto doctorScheduleSearchDto) {
		ModelAndView model = new ModelAndView();
		DoctorScheduleInforDto infor = appointmentService.getDoctorSchedule(doctorScheduleSearchDto);
		model.addObject("infor", infor);
		model.setViewName("appointment/doctor-schedule-detail");
		return model;
	}

	@ResponseBody
	@GetMapping(value = "/appointment/getDoctorScheduleDetail")
	public Object getDoctorScheduleDetail(
			@ModelAttribute("appointmentForm") DoctorScheduleSearchDto doctorScheduleSearchDto) {
		DoctorScheduleInforDto infor = appointmentService.getDoctorSchedule(doctorScheduleSearchDto);
		return infor;
	}

	@RequestMapping(value = "/appointment/addAppointment", method = RequestMethod.GET)
	public ModelAndView addArticle() {
		ModelAndView model = new ModelAndView();
		AppointmentDto appointment = new AppointmentDto();
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

	@PostMapping(value = "/appointment/saveAppointment")
	public ModelAndView save(@ModelAttribute("appointmentForm") AppointmentDto appointment) {
		ModelAndView model = new ModelAndView();
		try {
			appointmentService.saveApp(appointment);
			Employee emp = employeeRepository.findById(appointment.getEmployeeId()).get();
			Patient pat = patientRepository.findById(appointment.getPatientId()).get();
			String to = emp.getEmail();
			String subtitle = emp.getFirstName() + ", new appointment has been created!!";
			String msg = "You have an appointment with " + pat.getName() + " at " + appointment.getNote()
					+ " " + appointment.getDate() + ". Please check your current list.";
			mailService.sendSimpleEmail(to, subtitle, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setViewName("appointment/appointment-list");
		return model;
	}

	@ResponseBody
	@GetMapping(value = "/appointment/getShift")
	public Object getShift(@RequestParam("employeeId") Long id, @RequestParam("date") String date,
			@RequestParam("patientId") Long patientId) {
		AppointmentDto appointment = new AppointmentDto();
		Date date1;
		ShiftDto shiftDto = new ShiftDto();
		List<Shift> lstSfhit = new ArrayList<>();
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			appointment.setEmployeeId(id);
			appointment.setDate(date1);
			appointment.setPatientId(patientId);
			lstSfhit = appointmentService.getListShift(appointment);
			shiftDto.setLstShift(lstSfhit);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shiftDto;
	}

	@RequestMapping(value = "/appointment/export", method = RequestMethod.GET)
	public ModelAndView export(Long appId) {
		ModelAndView model = new ModelAndView();
		try {
			appointmentService.exportSlip(appId);
		} catch (Exception ex) {
			throw ex;
		}
		model.setViewName("appointment/appointment-list");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/appointment/exportSchedule", method = RequestMethod.GET)
	public ResponseEntity exportSchedule(
			@ModelAttribute("appointmentForm") DoctorScheduleSearchDto doctorScheduleSearchDto) throws Exception {
		String mess = "";
		try {
			mess = appointmentService.exportSchedule(doctorScheduleSearchDto.getEmployeeId(),
					doctorScheduleSearchDto.getDateFrom());
		} catch (Exception ex) {
			throw ex;
		}
		return new ResponseEntity<>(mess, HttpStatus.OK);
	}

	@RequestMapping(value = "/appointment/disable", method = RequestMethod.POST)
	@ResponseBody
	public Object disable(Long appId) {
		try {
			appointmentService.disableAppointment(appId);
		} catch (Exception ex) {
			throw ex;
		}
		return null;
	}
}
