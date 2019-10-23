package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Diagnosi;
import com.hospital.model.PrescriptionMedicine;
import com.hospital.model.dto.IllnessSuggestionDTO;

public interface PrescriptionMedicineRepository extends CrudRepository<PrescriptionMedicine, Integer> {
	
	@Query(value = "SELECT app_id FROM appointment", nativeQuery = true)
	public List<Integer> ListAllAppID();
	
	@Query(value = "SELECT * from prescription_medicine d WHERE d.app_id = ?1", nativeQuery = true)
	public List<PrescriptionMedicine> ListAllPrescriptionMedicineByAppID(Integer appID);
//	
//	@Query(value = "SELECT new com.hospital.model.dto.IllnessSuggestionDTO(i.illnessId, i.description, i.name, i.season, i.illnessType.name, Count(i.name))\r\n" + 
//			"from Examination e\r\n" + 
//			"INNER JOIN IllnessSymptom ils ON e.symptomId = ils.symptom.symptomId\r\n" + 
//			"INNER JOIN Illness i ON ils.illness.illnessId = i.illnessId\r\n" + 
//			"WHERE e.symptomId IN (SELECT ex.symptomId FROM Examination ex WHERE ex.patient.patientId = ?1)\r\n" + 
//			"GROUP BY i.name\r\n" + 
//			"ORDER BY Count(i.name) DESC")
//	public List<IllnessSuggestionDTO> ListAllSuggestIllnessByExaminationResult(Integer PatientID);
}
