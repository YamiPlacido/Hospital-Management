package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Diagnosi;
import com.hospital.model.dto.IllnessSuggestionDTO;

public interface DiagnosisRepository extends CrudRepository<Diagnosi, Long> {
	
	@Query(value = "SELECT app_id FROM appointment", nativeQuery = true)
	public List<Long> ListAllAppID();
	
	@Query(value = "SELECT * from diagnosis d WHERE d.app_id = ?1", nativeQuery = true)
	public List<Diagnosi> ListAllDiagnosisByAppID(Long appID);
	
	@Query(value = "SELECT new com.hospital.model.dto.IllnessSuggestionDTO(i.illnessId, i.description, i.name, i.season, i.illnessType.name, Count(i.name))\r\n" + 
			"from Examination e\r\n" + 
			"INNER JOIN IllnessSymptom ils ON e.symptomId = ils.symptom.symptomId\r\n" + 
			"INNER JOIN Illness i ON ils.illness.illnessId = i.illnessId\r\n" + 
			"WHERE e.symptomId IN (SELECT ex.symptomId FROM Examination ex WHERE ex.patient.patientId = ?1)\r\n" + 
			"GROUP BY i.name\r\n" + 
			"ORDER BY Count(i.name) DESC")
	public List<IllnessSuggestionDTO> ListAllSuggestIllnessByExaminationResult(Long PatientID);
}
