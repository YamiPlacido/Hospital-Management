package com.hospital.controller;


import com.hospital.dto.ExaminationDTO;
import com.hospital.dto.NotificationMessage;
import com.hospital.dto.PatientRecordDTO;
import com.hospital.model.*;
import com.hospital.repository.*;
import com.hospital.service.ConcurrencyService;
import com.hospital.service.HelperService;
import com.hospital.service.PDFExportService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Sets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "admin/doctor")
public class DoctorController {

    List<Appointment> todayAppointment = new ArrayList<>();
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    PositionRepository positionRepository;
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
    @Autowired
    ConcurrencyService pdfExportConcurrencyService;
    @Autowired
    PDFExportService pdfExportService;

    @GetMapping("personal-info/{id}")
    public ModelAndView viewDetail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("doctor/doctor-detail-info");
        Employee employee = employeeRepository.findById(id).get();
        Long specialityId = employee.getSpecialityId();
        Long positionId = employee.getPositionId();
        Speciality speciality = specialityRepository.findById(specialityId).get();
        Position position = positionRepository.findById(positionId).get();
        employee.setSpeciality(speciality);
        employee.setPosition(position);
        mav.addObject("employee",employee);
        return mav;
    }

    //Role Doctor, view himself, today
    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ModelAndView viewAllTodayAppointment() {
        ModelAndView mav = new ModelAndView("doctor/doctor-appointments");
        return mav;
    }

    @ResponseBody
    @GetMapping(value = "/api/appointments/{doctor_id}")
    public List<Appointment> viewAllTodayAppointmentAPI(@PathVariable("doctor_id") Long id) {
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
            @RequestParam(value = "selectedValue[]", required = false) String[] selectedValue) {
        List<AppointmentSymptom> list = new ArrayList<>();
        AppointmentSymptom entity;

        if (selectedValue.length == 0) {
            return "redirect:/doctor/appointment/" + appId;
        } else {
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
            return "redirect:/doctor/appointment/" + appId;
        }
    }

    //edit Appointment examination
    @ResponseBody
    @PostMapping("/api/examination")
    public ResponseEntity editAppointmentExaminations(
            @RequestParam("appId") Long appId,
            @RequestParam(value = "selectedValue[]", required = false) Long[] selectedValue) {
        Set<Long> oldState, newState, union;
        Set<Long> oldStateCanModified, oldStateCanNotModified;
        Set<Long> deleteMembers, createMembers, canNotDeleteMembers;
        //get new state
		if (selectedValue != null){
        	newState = new HashSet<>(Arrays.asList(selectedValue));
		} else {
			newState = new HashSet<>();
		}
        //get old state
        oldState = new HashSet<Long>(appointmentExaminationRepository.findExaminationTypeIdByAppointmentId(appId));
        //get old state status = CREATED
        oldStateCanModified = new HashSet<Long>(appointmentExaminationRepository.findExaminationTypeIdStatusCreatedByAppointmentId(appId));

        //computed properties
        union = new HashSet<>();
        union.addAll(oldState);
        union.addAll(newState);

        oldStateCanNotModified = new HashSet<>();
        oldStateCanNotModified.addAll(oldState);
        oldStateCanNotModified.removeAll(oldStateCanModified);

        deleteMembers = new HashSet<>();
        deleteMembers.addAll(union);
        deleteMembers.removeAll(newState);
        deleteMembers.removeAll(oldStateCanNotModified);

        canNotDeleteMembers = new HashSet<>();
        canNotDeleteMembers.addAll(union);
        canNotDeleteMembers.removeAll(newState);
        canNotDeleteMembers.retainAll(oldStateCanNotModified);

        createMembers = new HashSet<>();
        createMembers.addAll(union);
        createMembers.removeAll(oldState);

        List<Long> deleteMembersList = new ArrayList<>(deleteMembers);
        List<Long> createMembersList = new ArrayList<>(createMembers);
        NotificationMessage message = new NotificationMessage();

        if (canNotDeleteMembers.size() > 0) {
            message = new NotificationMessage();
            message.setMessage("Some exam may be on progress or finished and cannot be remove.");
            message.setStatus("danger");
        } else {
            if (deleteMembersList.size() >0 || createMembersList.size() > 0 ){
                AppointmentExamination entity;
                for (int i = 0; i < deleteMembersList.size(); i++) {
                    Long appointmentExaminationId = appointmentExaminationRepository.findIdByAppointmentIdAndExaminationTypeId(appId, deleteMembersList.get(i)).get(0);
                    AppointmentExamination appointmentExamination = appointmentExaminationRepository.findById(appointmentExaminationId).get();
                    //delete old exam
                    examinationRepository.delete(examinationRepository.findExaminationByAppointmentAndExaminationType(appId, deleteMembersList.get(i)).get(0));
                    //delete old record in join table
                    appointmentExaminationRepository.delete(appointmentExamination);
                }
                for (int i = 0; i < createMembersList.size(); i++) {
                    Long examinator_id = employeeRepository.findExaminatorsByExaminationType(createMembersList.get(i)).get(0).getEmployeeId();
                    Long patient_id = appointmentRepository.findById(appId).get().getPatient().getPatientId();
                    System.out.println("EXAMINATOR_ID" + examinator_id + "PATIENT_ID" + patient_id);
                    //điền mới vào bảng examination
                    examinationRepository.insertExamination(appId, createMembersList.get(i), examinator_id, patient_id);

                    //thêm vào bảng nối
                    Long examination_id = examinationRepository.findExaminationByAppointmentAndExaminationType(appId, createMembersList.get(i)).get(0).getId();
                    entity = new AppointmentExamination();
                    entity.setAppointmentId(appId);
                    entity.setExaminationId(examination_id);
                    appointmentExaminationRepository.save(entity);
                }
                message.setMessage("All your changes has take effect.");
                message.setStatus("success");
            } else {
                message.setMessage("You have change nothing.");
                message.setStatus("warning");
            }
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //create PDF request
    @ResponseBody
    @PostMapping("/api/examination/create_pdf")
    public ResponseEntity createExaminationRequestPdf(
            @RequestParam("appId") Long appId,
            @RequestParam("examinationTypeId") Long examinationTypeId) {
        Long examination_id = examinationRepository.findExaminationByAppointmentAndExaminationType(appId, examinationTypeId).get(0).getId();
        Examination ex = examinationRepository.findById(examination_id).get();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String strDate = dateFormat.format(date);
        String fileName = ex.getId()+"_request_"+strDate+".pdf";
        pdfExportService.createExaminationRequestPDF(ex,fileName);
//        pdfExportConcurrencyService.createExaminationRequestPDF(ex,fileName);
        ex.setPdfRequestPath(fileName);
        examinationRepository.save(ex);
        String mess = PDFExportService.EXPORT_FOLDER+fileName;
        return new ResponseEntity<>(mess,HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/api/appointments/symptom/{app_id}")
    public List<Symptom> getSymptomsByAppointment(@PathVariable("app_id") Long id) {
        List<AppointmentSymptom> list = appointmentSymptomRepository.findSymptomByAppointmentId(id);
        List<Symptom> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(symptomRepository.findById(list.get(i).getSymptomId()).get());
        }
        return result;
    }

    @ResponseBody
    @GetMapping(value = "/api/appointments/examination/{app_id}")
    public List<Examination> getExaminationByAppointment(@PathVariable("app_id") Long id) {
        List<AppointmentExamination> list = appointmentExaminationRepository.findExaminationByAppointmentId(id);
        List<Examination> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(examinationRepository.findById(list.get(i).getExaminationId()).get());
        }
        return result;
    }

    @ResponseBody
    @GetMapping(value = "/api/appointments/examination/full/{app_id}")
    public List<ExaminationDTO> getFullExaminationByAppointment(@PathVariable("app_id") Long id) {
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
            map.put(e.getExaminationType().getId(), e);
        }
        System.out.println("Map: " + map.toString());
        System.out.println("DTO size: " + examinationDTOS.size());
        for (int i = 0; i < examinationDTOS.size(); i++) {
            Long key = examinationDTOS.get(i).getId();

            if (map.containsKey(key)) {
                String stage = map.get(key).getStage();
                examinationDTOS.get(i).setStage(stage);
                System.out.println("Stage " + stage);
            }
        }
        return examinationDTOS;
    }

    @ResponseBody
    @PostMapping("/api/appointment/stage")
    public void editExamStage(
            @RequestParam("app_id") Long app_id,
            @RequestParam(value = "stage", required = false) String stage) {
        if (stage ==null){
            stage = "DOING";
        }
        Appointment app = appointmentRepository.findById(app_id).get();
        if (!app.getStage().contains("FINISHED")){
            app.setStage(stage);
            appointmentRepository.save(app);
        }
    }

    //only his patient (past and current appointment) & patient himself
    @ResponseBody
    @GetMapping(value = "api/record-detail/{patient_id}")
    public Map findPatientRecord(@PathVariable("patient_id") Long patient_id) {
        Map map = new HashMap();
        List<PatientRecordDTO> listRecord = new ArrayList<>();
        PatientRecordDTO temp;
        Patient patient = patientRepository.findById(patient_id).get();
        List<Long> listApp = new ArrayList<>();
        listApp = employeeRepository.findAllFinishedAppByPatientId(patient_id);
        patient = patientRepository.findById(patient_id).get();
        if (listApp.size()>0){
            for (int i = 0; i < listApp.size(); i++) {
                temp = new PatientRecordDTO();
                temp.setAppointment(appointmentRepository.findById(listApp.get(i)).get());
                List<Symptom> symptoms = getSymptomsByAppointment(listApp.get(i));
                List<Examination> examinations = getExaminationByAppointment(listApp.get(i));
                temp.setSymptoms(symptoms);
                temp.setExaminations(examinations);
                listRecord.add(temp);
            }
        }
        map.put("patient",patient);
        map.put("records",listRecord);
        return map;
    }

    public void editAppointmentSymptom(Long id) {
        todayAppointment = employeeRepository.findAppointmentByDoctorId(id);
    }

    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public ModelAndView getPatientFromAppointment() {
        ModelAndView mav = new ModelAndView("doctor/patient-records");
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
