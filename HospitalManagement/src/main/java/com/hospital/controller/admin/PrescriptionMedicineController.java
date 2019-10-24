package com.hospital.controller.admin;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Diagnosi;
import com.hospital.model.PrescriptionMedicine;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.service.AppointmentService;
import com.hospital.service.DiagnosisService;
import com.hospital.service.IllnessService;
import com.hospital.service.PrescriptionMedicineService;

@Controller
@RequestMapping(value = "/admin/prescription")
public class PrescriptionMedicineController {

	@Autowired
	PrescriptionMedicineService pservice;
	@Autowired
	AppointmentService aservice;
	@Autowired
	IllnessService iservice;
	
//	@RequestMapping(value = "/getAllDianosis", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object getAllDiagnosis() {
//		List<Diagnosi> listDiagnosis = service.ListAllDianosis();
//		return listDiagnosis;	
//	}
//	
	@RequestMapping(value = "/getPrescriptionMedicineByID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getPrescriptionMedicineByID(@PathVariable Long id) {
		PrescriptionMedicine pres = pservice.GetPrescriptionMedicineByID(id);
		return pres;
	}
	
	@RequestMapping(value = "/getPrescriptionMedicineByAppID/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getPrescriptionMedicineByAppID(@PathVariable Long id) {
		List<PrescriptionMedicine> pres = pservice.ListAllPrescriptionMedicineByAppID(id);
		return pres;
	}
	
//	@RequestMapping(value = "/getAllIllnessSuggestionByExaminationResult/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Object getAllIllnessSuggestionByExaminationResult(@PathVariable Long id) {
//		List<IllnessSuggestionDTO> illsug = service.ListAllSuggestIllnessByExaminationResult(id);
//		return illsug;
//	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView SaveData(PrescriptionMedicine pres) {
		try {
			pservice.SaveData(pres);
		}catch(Exception ex){
			throw ex;
		}
		return new ModelAndView("redirect:/admin/procedure/profile/"+pres.getAppointment().getAppId());
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object Delete(Long id) {
		try {
			pservice.Delete(id);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
	
}
