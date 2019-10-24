package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.model.Patient;
import com.hospital.model.dto.DiagnosiDTO;
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
	 public Patient getPatientById(long patientId) {
	  return patientRepository.findById(patientId).get();
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
	 public void disablePatient(long patientId) {
		 Patient patient = patientRepository.findById(patientId).get();
		 patient.setStatus(false);
		 patientRepository.save(patient);
	 }
	 

		@Override
		public List<Patient> ListAllPatient() {
			List<Patient> listPatient = (List<Patient>) patientRepository.findAll();
			return listPatient;
		}

		@Override
		public Patient GetPatientByID(Long id) {
			Patient patient = patientRepository.findById(id).get();
			return patient;
		}

		@Override
		public void SaveData(Patient patient) {
			patientRepository.save(patient);		
		}

		@Override
		public void Delete(Long id) {
			patientRepository.deleteById(id);		
		}

		@Override
		public List<DiagnosiDTO> ListAllPatientIllnessByID(Long id) {
			List<DiagnosiDTO>  patientIllness = (List<DiagnosiDTO>) patientRepository.GetAllDiagnosisByPatientID(id);
			return patientIllness;
		}

}

