package com.hospital.controller;

import com.hospital.model.AppointmentSymptom;
import com.hospital.model.ExaminationType;
import com.hospital.model.Speciality;
import com.hospital.model.Symptom;
import com.hospital.repository.AppointmentSymptomRepository;
import com.hospital.repository.EmployeeRepository;
import com.hospital.repository.SpecialityRepository;
import com.hospital.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/system")
public class SystemController {
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AppointmentSymptomRepository appointmentSymptomRepository;
    @Autowired
    SymptomRepository symptomRepository;

    @GetMapping(value = "/symptoms/type_one")
    public List<Symptom> findAllSymptomTypeOne(){
        return employeeRepository.fillSymptomTypeOne();
    }

    @GetMapping(value = "/symptoms/type_two")
    public List<Symptom> findAllSymptomTypeTwo(){
        return employeeRepository.fillSymptomTypeTwo();
    }

    @GetMapping(value = "/examinations")
    public Set<ExaminationType> findAllExamination(){
        return employeeRepository.findAllExamination();
    }

    @GetMapping(value = "/examinations/suggest/{app_id}")
    public Set<ExaminationType> findAllExaminationWithSuggest(@PathVariable("app_id") Long appointmentId){
        Set<ExaminationType> allExamType = new HashSet<>();
        allExamType.addAll(employeeRepository.findAllExamination());
        Set<Long> suggestExamTypeId = new HashSet<>();
        List<Long> found;
        Long examinationTypeId;
        List<AppointmentSymptom> list = appointmentSymptomRepository.findSymptomByAppointmentId(appointmentId);
        for (int i = 0; i < list.size(); i++) {
            found  = employeeRepository.findExaminationTypeBySymptomId(list.get(i).getSymptomId());
            if (found.size()>0){
                examinationTypeId = found.get(0);
                suggestExamTypeId.add(examinationTypeId);
            }
        }
        for (ExaminationType ex : allExamType) {
            if (suggestExamTypeId.contains(ex.getId())){
                ex.setSuggested(true);
            }
        }
        return allExamType;
    }

    @RequestMapping(value = "/symptom", method = RequestMethod.GET)
    public HashMap<Integer, String> getSymptomOfAppointment(@RequestParam int appointment_id) {
        return null;
    }

    @RequestMapping(value = "/symptom", method = RequestMethod.POST)
    public void editSymptomOfAppointment(@RequestParam int appointment_id) {
        //check appointment status  != FINISHED
        //check role Doctor or Receptionist

    }
    @RequestMapping(value = "/speciality", method = RequestMethod.GET)
    public List<Speciality> getSpecialityByPositionId(@RequestParam Long position_id) {
        List<Speciality> result = specialityRepository.findByPositionId(position_id);
        return result;
    }
}
