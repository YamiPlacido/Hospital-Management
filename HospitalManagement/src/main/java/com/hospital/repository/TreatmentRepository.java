package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Treatment;
import com.hospital.model.TreatmentMethodIllness;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.model.dto.TreatmentSuggestionDTO;

public interface TreatmentRepository extends CrudRepository<Treatment, Long> {
	
	@Query(value = "SELECT illness_id FROM illness", nativeQuery = true)
	public List<Integer> ListAllIllnessID();

	@Query(value = "SELECT * FROM treatment t where t.app_id = ?1", nativeQuery = true)
	public List<Treatment> ListAllTreatmentByAppID(Long appID);
	
	@Query(value = "SELECT new com.hospital.model.dto.TreatmentSuggestionDTO(tm.treatmentMethodId, tm.detail, tm.name, Count(tm.name))\r\n" + 
			"from Diagnosi d\r\n" + 
			"INNER JOIN Treatment t ON d.illness.illnessId = t.illness.illnessId\r\n" + 
			"INNER JOIN TreatmentMethod tm ON tm.treatmentMethodId = t.treatmentMethod.treatmentMethodId\r\n" + 
			"WHERE d.illness.illnessId IN (SELECT dx.illness.illnessId FROM Diagnosi dx WHERE dx.appointment.appId = ?1)\r\n" + 
			"GROUP BY tm.name\r\n" + 
			"ORDER BY Count(tm.name) DESC")
	public List<TreatmentSuggestionDTO> ListAllSuggestTreatmentByAppID(Long AppID);
}
