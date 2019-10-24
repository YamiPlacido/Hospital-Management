package com.hospital.service;
import java.util.List;
import com.hospital.model.*;

public interface PatientService {  

	 public List<Patient> getAllPatient();
	 
	 public Patient getPatientById(long patientId);
	 
	 public void save(Patient patient);
	 
	 public void saveUpdate(Patient patient);
	 
	 public void disablePatient(long patientId);
	
}
