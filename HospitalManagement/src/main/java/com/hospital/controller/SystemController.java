package com.hospital.controller;

import com.hospital.model.ExaminationType;
import com.hospital.model.Speciality;
import com.hospital.model.Symptom;
import com.hospital.repository.EmployeeRepository;
import com.hospital.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/system")
public class SystemController {
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(value = "/symptoms")
    public Set<Symptom> findAllSymptom(){
        return employeeRepository.findAllSymptom();
    }

    @GetMapping(value = "/examinations")
    public Set<ExaminationType> findAllExamination(){
        return employeeRepository.findAllExamination();
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
    public List<Speciality> getSpecialityByPositionId(@RequestParam int position_id) {
        List<Speciality> result = specialityRepository.findByPositionId(position_id);
        return result;
    }
}
