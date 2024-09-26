package com.hospital.service;

import java.util.List;

import com.hospital.model.Patient;
import com.hospital.model.dto.DiagnosiDTO;

public interface PatientService {
	
	public List<Patient> ListAllPatient();
	
	public Patient GetPatientByID(Integer id);
	
	public void SaveData(Patient Patient);
	
	public void Delete(Integer id);
	
	public List<DiagnosiDTO> ListAllPatientIllnessByID(Integer id);
}
