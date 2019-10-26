
package com.hospital.controller;

import com.hospital.model.Examination;
import com.hospital.repository.ExaminationRepository;
import com.hospital.service.MailService;
import com.hospital.service.ConcurrencyService;
import com.hospital.service.PDFExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hospital.repository.EmployeeRepository;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/examinator")
public class ExaminatorController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ExaminationRepository examinationRepository;
    @Autowired
    PDFExportService pdfExportService;
    @Autowired
    ConcurrencyService pdfExportConcurrencyService;
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/examinations", method = RequestMethod.GET)
    public ModelAndView viewAllExamToday() {
        ModelAndView mav = new ModelAndView("doctor/examinator-exams");
        return mav;
    }

    @ResponseBody
    @GetMapping(value = "/api/examinations/{examinator_id}")
    public List<Examination> viewAllTodayAppointmentAPI(@PathVariable("examinator_id") Long id) {
        return employeeRepository.findUnfinishedExaminationsByExaminatorId(id);
    }

    @ResponseBody
    @GetMapping(value = "/api/examination/{examination_id}")
    public Examination viewExaminationById(@PathVariable("examination_id") Long id) {
        return examinationRepository.findById(id).get();
    }

    //edit Examination content
    @ResponseBody
    @PostMapping("/api/examination/content")
    public void editExamContent(
            @RequestParam("ex_id") Long ex_id,
            @RequestParam(value = "content", required = false) String content) {
        Examination ex = examinationRepository.findById(ex_id).get();
        ex.setContent(content);
        examinationRepository.save(ex);
    }

	//edit Examination result
	@ResponseBody
	@PostMapping("/api/examination/result")
	public void editExamResult(
			@RequestParam("ex_id") Long ex_id,
			@RequestParam(value = "content", required = false) String content) {
		Examination ex = examinationRepository.findById(ex_id).get();
		ex.setResult(content);
		examinationRepository.save(ex);
	}

    //finish Examination content
    @ResponseBody
    @PostMapping("/api/examination")
    public void finishExamination(
            @RequestParam("ex_id") Long ex_id,
            @RequestParam(value = "content", required = false) String content) {
        Examination ex = examinationRepository.findById(ex_id).get();
        ex.setStage("FINISHED");
        examinationRepository.save(ex);
//        pdfExportService.createPDF();
        pdfExportConcurrencyService.createPDF();
//        mailService.sendSimpleEmail("hello@yahoo.com","autum7k@yahoo.com","From Spring boot","Hi");
    }

}



