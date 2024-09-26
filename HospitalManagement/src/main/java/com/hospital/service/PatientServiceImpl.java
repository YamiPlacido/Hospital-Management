package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Diagnosi;
import com.hospital.model.Illness;
import com.hospital.model.IllnessType;
import com.hospital.model.Patient;
import com.hospital.model.dto.DiagnosiDTO;
import com.hospital.repository.IllnessRepository;
import com.hospital.repository.IllnessTypeRepository;
import com.hospital.repository.PatientRepository;

@Component
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepository repo;

	@Override
	public List<Patient> ListAllPatient() {
		List<Patient> listPatient = (List<Patient>) repo.findAll();
		return listPatient;
	}

	@Override
	public Patient GetPatientByID(Integer id) {
		Patient patient = repo.findById(id).get();
		return patient;
	}

	@Override
	public void SaveData(Patient patient) {
		repo.save(patient);		
	}

	@Override
	public void Delete(Integer id) {
		repo.deleteById(id);		
	}

	@Override
	public List<DiagnosiDTO> ListAllPatientIllnessByID(Integer id) {
		List<DiagnosiDTO>  patientIllness = (List<DiagnosiDTO>) repo.GetAllDiagnosisByPatientID(id);
		return patientIllness;
	}

}
