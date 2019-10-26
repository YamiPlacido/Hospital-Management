package com.hospital.controller;


import com.hospital.dto.ExaminationDTO;
import com.hospital.model.*;
import com.hospital.repository.*;
import com.hospital.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;



@Controller
@RequestMapping(value = "admin/doctor")
public class DoctorController {
	List<Appointment> todayAppointment = new ArrayList<>();
	@Autowired
    EmployeeRepository employeeRepository;
	@Autowired
	AppointmentRepository appointmentRepository;
	@Autowired
    AppointmentSymptomRepository appointmentSymptomRepository;
	@Autowired
	AppointmentExaminationRepository appointmentExaminationRepository;
	@Autowired
    ExaminationRepository examinationRepository;
	@Autowired
	SymptomRepository symptomRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	HelperService helperService;
	@Autowired
	ExaminationTypeRepository examinationTypeRepository;
	//Role Doctor, view himself, today
	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public ModelAndView viewAllTodayAppointment() {
		ModelAndView mav = new ModelAndView("doctor/doctor-appointments");
		return mav;
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/{doctor_id}")
	public List<Appointment> viewAllTodayAppointmentAPI(@PathVariable("doctor_id") Long id){
		todayAppointment = employeeRepository.findAppointmentByDoctorId(id);
		return todayAppointment;
//		Patient patient = new Patient();
//		List<Appointment> todayAppointment = employeeRepository.findAppointmentByDoctorId(id);
//		for (int i = 0; i <todayAppointment.size() ; i++) {
//			patient = patientRepository.findById(todayAppointment.get(i).getPatientId()).get();
//			todayAppointment.get(i).setPatient(patient);
//		}
//		return todayAppointment;
	}

	@ResponseBody
	@GetMapping(value = "/api/appointment/{app_id}")
	public Appointment viewExaminationById(@PathVariable("app_id") Long id) {
//		Appointment appointment  = new Appointment();
//		appointment = appointmentRepository.findById(id).get();
//		Patient patient = patientRepository.findById(appointment.getPatientId()).get();
//		appointment.setPatient(patient);
//		return appointment;
		return appointmentRepository.findById(id).get();
	}

	//edit Appointment symptoms
	@ResponseBody
	@PostMapping("/api/symptom")
	public String editAppointmentSymptoms(
			@RequestParam("appId") Long appId,
			@RequestParam(value="selectedValue[]",required = false) String[] selectedValue) {
		List<AppointmentSymptom> list = new ArrayList<>();
		AppointmentSymptom entity;

		if (selectedValue.length==0) {
			return "redirect:/doctor/appointment/"+appId;
		}
		else
			{
			list = appointmentSymptomRepository.findSymptomByAppointmentId(appId);
			for (int i = 0; i < list.size(); i++) {
				appointmentSymptomRepository.delete(list.get(i));
			}

			for (int i = 0; i < selectedValue.length; i++) {
				entity = new AppointmentSymptom();
				entity.setAppointmentId(appId);
				entity.setSymptomId(Long.valueOf(selectedValue[i]));
				appointmentSymptomRepository.save(entity);
			}
			return "redirect:/doctor/appointment/"+appId;
		}
	}

	//edit Appointment examination
	@ResponseBody
	@PostMapping("/api/examination")
	public String editAppointmentExaminations(
			@RequestParam("appId") Long appId,
			@RequestParam(value="selectedValue[]",required = false) String[] selectedValue) {
		List<AppointmentExamination> list = new ArrayList<>();
		AppointmentExamination entity;
		Examination examination = new Examination();
//		tìm các exam của appointment, nếu chưa finished thì xóa
		list = appointmentExaminationRepository.findExaminationByAppointmentId(appId);
		if (list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				AppointmentExamination appointmentExamination = list.get(i);
				Long ex_id = appointmentExamination.getExaminationId();
				//check examination stage
				String stage = examinationRepository.findById(ex_id).get().getStage();
				if (stage != "FINISHED"){ //stage khác finished thì delete ở 2 bảng
					//delete old record in join table
					appointmentExaminationRepository.delete(appointmentExamination);
					//delete old exam (if status == ?)
					examinationRepository.delete(examinationRepository.findById(ex_id).get());
				}
			}
		}
		if (selectedValue==null) {
			return "redirect:/doctor/appointment/"+appId;
		}
		else
		{
			for (int i = 0; i < selectedValue.length; i++) {
				int examination_type_id = Integer.parseInt(selectedValue[i]);
				System.out.println("EXAMINATION_TYPE_ID "+ examination_type_id);
				Long examination_id;
				//kiểm tra xem có chưa, chưa có thì insert
				if (examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).size()==0){
						Long examinator_id = employeeRepository.findExaminatorsByExaminationType(examination_type_id).get(0).getEmployeeId();
						Long patient_id = appointmentRepository.findById(appId).get().getPatient().getPatientId();
						System.out.println("EXAMINATOR_ID"+examinator_id+"PATIENT_ID"+patient_id);
						//điền mới vào bảng examination
						examinationRepository.insertExamination(appId,examination_type_id,examinator_id,patient_id);

						//thêm vào bảng nối
						examination_id = examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).get(0).getId();
						entity = new AppointmentExamination();
						entity.setAppointmentId(appId);
						entity.setExaminationId(examination_id);
						appointmentExaminationRepository.save(entity);

				} else //có rồi thì giữ nguyên
					{
					Examination e = examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).get(0);
					if (e.getStage() != "FINISHED"){

					}
//					examination=examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).get(0);
//					examinationRepository.delete(examination);
				}
			}
			return "redirect:/doctor/appointment/"+appId;
		}
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/symptom/{app_id}")
	public List<Symptom> getSymptomsByAppointment(@PathVariable("app_id") Long id){
		List<AppointmentSymptom> list = appointmentSymptomRepository.findSymptomByAppointmentId(id);
		List<Symptom> result = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			result.add(symptomRepository.findById(list.get(i).getSymptomId()).get());
		}
		return result;
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/examination/{app_id}")
	public List<Examination> getExaminationByAppointment(@PathVariable("app_id") int id){
		List<AppointmentExamination> list = appointmentExaminationRepository.findExaminationByAppointmentId(id);
		List<Examination> result = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			result.add(examinationRepository.findById(list.get(i).getExaminationId()).get());
		}
		return result;
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/examination/full/{app_id}")
	public List<ExaminationDTO> getFullExaminationByAppointment(@PathVariable("app_id") int id){
		List<ExaminationDTO> examinationDTOS = new ArrayList<>();
		ExaminationDTO temp;
		List<ExaminationType> fullList = new ArrayList<>();
		fullList = examinationTypeRepository.findAll();
		for (int i = 0; i < fullList.size(); i++) {
			temp = new ExaminationDTO();
			temp.setId(fullList.get(i).getId());
			temp.setName(fullList.get(i).getName());
			examinationDTOS.add(temp);
		}
		Map<Long, Examination> map = new HashMap<>();
		List<AppointmentExamination> list = appointmentExaminationRepository.findExaminationByAppointmentId(id);
		for (int i = 0; i < list.size(); i++) {
			Examination e = examinationRepository.findById(list.get(i).getExaminationId()).get();
			map.put(e.getExaminationType().getId(),e);
		}
		System.out.println("Map: " + map.toString());
		System.out.println("DTO size: " + examinationDTOS.size());
		for (int i = 0; i < examinationDTOS.size(); i++) {
			Long key = examinationDTOS.get(i).getId();

			if (map.containsKey(key)){
				String stage = map.get(key).getStage();
				examinationDTOS.get(i).setStage(stage);
				System.out.println("Stage " + stage);
			}
		}
		return examinationDTOS;
	}

	public void editAppointmentSymptom(Long id){
		todayAppointment = employeeRepository.findAppointmentByDoctorId(id);
	}

	@RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
	public ModelAndView getPatientFromAppointment() {
		ModelAndView mav = new ModelAndView("doctor/patient-records");
		return mav;
	}
	//only his patient (past and current appointment) & patient himself
	@RequestMapping(value = "/record-detail/{id}", method = RequestMethod.GET)
	public ModelAndView findPatientRecord() {
		ModelAndView mav = new ModelAndView("doctor/patient-record-detail");
		return mav;
	}

	@RequestMapping(value = "/examination-detail/{id}", method = RequestMethod.GET)
	public ModelAndView viewExaminationDetail() {
		ModelAndView mav = new ModelAndView("doctor/patient-examination-detail");
		return mav;
	}


	//Role Doctor, view himself, today
	@RequestMapping(value = "/appointment-unfinish", method = RequestMethod.GET)
	public void viewUnfinishAppointment() {

	}

	//get from system
	@RequestMapping(value = "/medicine", method = RequestMethod.GET)
	public void getMedicineForIllness() {

	}

}
