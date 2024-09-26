package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Patient;
import com.hospital.model.dto.DiagnosiDTO;

public interface PatientRepository extends CrudRepository<Patient, Integer> {		
	
	@Query(value = "SELECT new com.hospital.model.dto.DiagnosiDTO(d.diagnosisId, d.appointment.appId, d.illness.name, d.degree, d.dianogsisTime, d.createdDate)\r\n" + 
			"from Patient p\r\n" + 
			"LEFT JOIN Appointment a ON p.patientId = a.patient.patientId\r\n" + 
			"LEFT JOIN Diagnosi d ON a.appId = d.appointment.appId\r\n" + 
			"WHERE p.patientId = ?1")
	public List<DiagnosiDTO> GetAllDiagnosisByPatientID(Integer id);
}
