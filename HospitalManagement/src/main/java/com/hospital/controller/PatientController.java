package com.hospital.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hospital.model.Patient;
import com.hospital.service.PatientService;

@Controller
@RequestMapping(value = "/admin")
public class PatientController {

	@Autowired
	PatientService patientService;
	
	@RequestMapping(value = "/patient/patient", method = RequestMethod.GET)
	public ModelAndView appointment() {
		ModelAndView model = new ModelAndView();
		model.setViewName("patient/patient-list");

		return model;
	}

	@RequestMapping(value = "/patient/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object list() {
		List<Patient> patientList = patientService.getAllPatient();

		return patientList;
	}


	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {

		// Convert multipart object to byte[]
		binder.registerCustomEditor(String.class, new StringMultipartFileEditor());

	}

	@RequestMapping(value = "/patient/addPatient/", method = RequestMethod.GET)
	public ModelAndView addArticle() {
		ModelAndView model = new ModelAndView();

		Patient patient = new Patient();
		model.addObject("patientForm", patient);
		model.setViewName("patient/patient-form");

		return model;
	}

	@RequestMapping(value = "/patient/update/{patientId}", method = RequestMethod.GET)
	public ModelAndView editArticle(@PathVariable long patientId) {
		ModelAndView model = new ModelAndView(); 
		Patient patient = patientService.getPatientById(patientId);
		model.addObject("patientForm2", patient);
		model.setViewName("patient/patient-form2");

		return model;
	}

	// validate trong controller
	// up image (NewsController trong SpringJpaCrud)
	@RequestMapping(value = "/patient/savePatient", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("patientForm") Patient patient,
			@RequestParam("imageUrl") MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(
					"E:\\Hospital-Trang\\Hospital-Trang\\img\\"
							+ file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
			patient.setImageUrl(path.toString());
			patientService.save(patient);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ModelAndView model1 = new ModelAndView();
		model1.setViewName("patient/patient-list");

		return model1;
		
	}
	
	@RequestMapping(value = "/patient/saveUpdate", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("patientForm2") Patient patient) {
		patientService.saveUpdate(patient);

		ModelAndView model = new ModelAndView();
		model.setViewName("patient/patient-list");

		return model;
	}

	
	@RequestMapping(value="/patient/disable", method = RequestMethod.POST)
	@ResponseBody
	public Object disable(Long patientId) {
		try {
			patientService.disablePatient(patientId);
		}catch(Exception ex) {
			throw ex;
		}
		return null;
	}
}
