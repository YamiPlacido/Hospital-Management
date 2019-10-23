package com.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Medicine;
import com.hospital.service.EmployeeService;
import com.hospital.service.MedicineService;
import com.hospital.service.PatientService;

@Controller
@RequestMapping(value = "/admin")

public class MedicineController {

	@Autowired
	MedicineService medicineService;
	@Autowired
	PatientService patientService;
	@Autowired
	EmployeeService employeeService;

 
	 
	
	@RequestMapping(value = "/medicine/medicine", method = RequestMethod.GET)
	public ModelAndView medicine() {
		ModelAndView model = new ModelAndView();
		model.setViewName("medicine/medicine-list");

		return model;
	}

	@RequestMapping(value = "/medicine/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object list() {
		List<Medicine> medicineList = medicineService.getAllMedicine(); 
		return medicineList;
	}

	 

	@RequestMapping(value = "/medicine/addMedicine/", method = RequestMethod.GET)
	public ModelAndView addArticle() {
		ModelAndView model = new ModelAndView();
		Medicine medicine = new Medicine(); 
		model.addObject("medicineForm", medicine);
		model.setViewName("medicine/medicine-form");

		return model;
	}

	@RequestMapping(value = "/medicine/update/{appId}", method = RequestMethod.GET)
	public ModelAndView editArticle(@PathVariable long appId) {
		ModelAndView model = new ModelAndView();
		Medicine medicine = medicineService.getMedicineById(appId);
		model.addObject("medicineForm2", medicine);
		model.setViewName("medicine/medicine-form2");

		return model;
	}

	@RequestMapping(value = "/medicine/saveUpdate", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("medicineForm2") Medicine Medicine) {
		medicineService.saveOrUpdate(Medicine);

		ModelAndView model = new ModelAndView();
		model.setViewName("medicine/medicine-list");

		return model;
	}
	
	// validate trong controller
	// up image (NewsController trong SpringJpaCrud)
	@RequestMapping(value = "/medicine/saveMedicine", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("medicineForm") Medicine medicine) {
		medicineService.saveOrUpdate(medicine);
		ModelAndView model = new ModelAndView();
		model.setViewName("medicine/medicine-list");
		return model;
	}
 
	@RequestMapping(value="/medicine/disable", method = RequestMethod.POST)
	@ResponseBody
	public Object disable(Long medicineID) {
		try {
			medicineService.disableMedicine(medicineID);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
}
