package com.hospital.controller.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.dto.EmptyJsonResponse;
import com.hospital.model.Appointment;
import com.hospital.model.Diagnosi;
import com.hospital.model.Illness;
import com.hospital.model.Medicine;
import com.hospital.model.Patient;
import com.hospital.model.PrescriptionMedicine;
import com.hospital.model.Treatment;
import com.hospital.model.TreatmentMethod;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.model.dto.TreatmentSuggestionDTO;
import com.hospital.service.AppointmentService;
import com.hospital.service.DiagnosisService;
import com.hospital.service.IllnessService;
import com.hospital.service.MedicalReportService;
import com.hospital.service.MedicineService;
import com.hospital.service.PatientService;
import com.hospital.service.PrescriptionMedicineService;
import com.hospital.service.TreatmentMethodIllnessService;
import com.hospital.service.TreatmentMethodService;
import com.hospital.service.TreatmentService;

@Controller
@RequestMapping(value = "/admin/procedure")
public class ProcedureController {
	
	@Autowired
	PatientService pservice;
	
	@Autowired
	AppointmentService aservice;
	
	@Autowired
	DiagnosisService dservice;
	
	@Autowired
	IllnessService iservice;
	
	@Autowired
	TreatmentService tservice;
	
	@Autowired
	TreatmentMethodService tmservice;
	
	@Autowired
	MedicineService mservice;
	
	@Autowired
	PrescriptionMedicineService pmservice;
	
	@Autowired
	MedicalReportService mrservice;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("procedure/procedure-index");
		return mv;
	}
	
	@RequestMapping(value = "/profile/{appID}", method = RequestMethod.GET)
	public ModelAndView DisplayIllnessProfile(@PathVariable Long appID) {
		ModelAndView mv = new ModelAndView("procedure/procedure-profile");
		Long patientID = aservice.GetPatientIDByAppID(appID);
		Diagnosi diag = new Diagnosi();
		Treatment treatment = new Treatment();
		PrescriptionMedicine pres = new PrescriptionMedicine();
		List<IllnessSuggestionDTO> illsug = dservice.ListAllSuggestIllnessByExaminationResult(patientID);
		List<Illness> illall = iservice.ListAllIllness();
		List<Diagnosi> diagall = dservice.ListAllDiagnosisByAppID(appID);
		List<TreatmentSuggestionDTO> treatsug = tservice.ListAllSuggestTreatmentByAppID(appID);
		List<TreatmentMethod> treatall = tmservice.ListAllTreatmentMethod();
		List<Medicine> presall = mservice.getAllMedicine();
		mv.addObject("patientID",patientID);
		mv.addObject("appID",appID);
		mv.addObject("diag",diag);
		mv.addObject("illsug",illsug);
		mv.addObject("illall",illall);
		mv.addObject("diagall",diagall);
		mv.addObject("treatment",treatment);
		mv.addObject("treatsug",treatsug);
		mv.addObject("treatall",treatall);
		mv.addObject("pres",pres);
		mv.addObject("presall",presall);
		return mv;
	}
	
	@RequestMapping(value = "/profile/treatsug/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllTreatsug(@PathVariable Long id) {
		List<TreatmentSuggestionDTO> treatsug = tservice.ListAllSuggestTreatmentByAppID(id);
		return treatsug;
	}
//	
//	@ResponseBody
//	@RequestMapping(value="/appointment/exportSchedule", method = RequestMethod.GET)
//	public ResponseEntity exportSchedule(@ModelAttribute("appointmentForm") DoctorScheduleSearchDto doctorScheduleSearchDto) throws Exception {
//		try {
//			mrservice.exportSchedule(doctorScheduleSearchDto.getEmployeeId(),doctorScheduleSearchDto.getDateFrom());
//		}catch(Exception ex) {
//			throw ex;
//		}
//		return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK); 
//	}
	
	@RequestMapping(value="/export/{appID}", method = RequestMethod.GET)
	public ModelAndView export(@PathVariable Long appID) throws Exception {
		try {
			Appointment app = aservice.getAppointmentById(appID);
			List<Diagnosi> alldiag = dservice.ListAllDiagnosisByAppID(appID);
			List<Treatment> alltreat = tservice.ListAllTreatmentByAppID(appID);
			List<PrescriptionMedicine> allpres = pmservice.ListAllPrescriptionMedicineByAppID(appID);
			mrservice.MedicalReport(app, alldiag, alltreat, allpres);
		}catch(Exception ex) {
			throw ex;
		}
		return new ModelAndView("redirect:/admin/procedure/profile/"+appID);

	}
}
