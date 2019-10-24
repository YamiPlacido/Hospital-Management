package com.hospital.service;

import java.util.List;

import com.hospital.model.Patient;
import com.hospital.model.dto.DiagnosiDTO;

public interface PatientService {

	public List<Patient> getAllPatient();

	public Patient getPatientById(long patientId);

	public void save(Patient patient);

	public void saveUpdate(Patient patient);

	public void disablePatient(long patientId);

	public List<Patient> ListAllPatient();

	public Patient GetPatientByID(Long id);

	public void SaveData(Patient Patient);

	public void Delete(Long id);

	public List<DiagnosiDTO> ListAllPatientIllnessByID(Long id);

}
