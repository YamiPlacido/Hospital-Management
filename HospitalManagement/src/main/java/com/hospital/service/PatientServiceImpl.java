package com.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hospital.model.*;
import com.hospital.repository.PatientRepository;

@Service
@Transactional
@Repository
public class PatientServiceImpl implements PatientService {
   
	 
	 @Autowired
	 PatientRepository patientRepository;

	 @Override
	 public List<Patient> getAllPatient() {
	  return (List<Patient>) patientRepository.findAll();
	 }

	 @Override
	 public Patient getPatientById(long patientID) {
	  return patientRepository.findById(patientID).get();
	 }

	 @Override
	 public void save(Patient patient) {
		 patient.setStatus(true);
		 patientRepository.save(patient);
	 }
	 @Override
	 public void saveUpdate(Patient patient) {
		 patientRepository.save(patient);
	 }

	 @Override
	 public void disablePatient(long patientID) {
		 Patient patient = patientRepository.findById(patientID).get();
		 patient.setStatus(false);
		 patientRepository.save(patient);
	 }

}

