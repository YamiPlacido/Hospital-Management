
package com.hospital.controller;

import com.hospital.dto.ExaminationRequestTemplateDTO;
import com.hospital.model.Examination;
import com.hospital.repository.ExaminationRepository;
import com.hospital.service.MailService;
import com.hospital.service.ConcurrencyService;
import com.hospital.service.PDFExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.repository.EmployeeRepository;

import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        return employeeRepository.findAllTodayExaminationsByExaminatorId(id);
    }

    @ResponseBody
    @GetMapping(value = "/api/examinations/unfinished/{examinator_id}")
    public List<Examination> viewAllTodayUnfinishedAppointmentAPI(@PathVariable("examinator_id") Long id) {
        return employeeRepository.findUnfinishedExaminationsByExaminatorId(id);
    }

    @ResponseBody
    @GetMapping(value = "/api/examination/{examination_id}")
    public Examination viewExaminationById(@PathVariable("examination_id") Long id) {
        return examinationRepository.findById(id).get();
    }

    //edit Examination content
    @ResponseBody
    @PostMapping("/api/examination/stage")
    public void editExamStage(
            @RequestParam("ex_id") Long ex_id,
            @RequestParam(value = "content", required = false) String stage) {
        if (stage ==null){
            stage = "DOING";
        }
        Examination ex = examinationRepository.findById(ex_id).get();
        ex.setStage(stage);
        examinationRepository.save(ex);
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

    //create result PDF Examination
    @ResponseBody
    @PostMapping("/api/examination/create_pdf")
    public ResponseEntity createPdf(
            @RequestParam("ex_id") Long ex_id,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "result", required = false) String result) {
        Examination ex = examinationRepository.findById(ex_id).get();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String strDate = dateFormat.format(date);
        String fileName = ex.getId()+"_result_"+strDate+".pdf";
        pdfExportService.createExaminationResultPDF(ex,fileName,content,result);
        ex.setPdfResultPath(fileName);
        examinationRepository.save(ex);
        String mess = PDFExportService.EXPORT_FOLDER+fileName;
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

    //finish Examination content
    @ResponseBody
    @PostMapping("/api/examination")
    public void finishExamination(
            @RequestParam("ex_id") Long ex_id,
            @RequestParam(value = "content", required = false) String content) {
        Examination ex = examinationRepository.findById(ex_id).get();
        ex.setStage("FINISHED");
        String to = ex.getAppointment().getEmployee().getEmail();
        String subtitle = "Exam code " + ex.getId() + " has done";
        String msg = "Your exam you have requested has done. Please check the result.";
        mailService.sendSimpleEmail(to,subtitle,msg);
        examinationRepository.save(ex);
    }

}



