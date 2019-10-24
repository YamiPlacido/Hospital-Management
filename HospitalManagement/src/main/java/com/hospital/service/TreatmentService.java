package com.hospital.service;

import java.util.List;

import com.hospital.model.Treatment;
import com.hospital.model.dto.TreatmentSuggestionDTO;

public interface TreatmentService {
	
	public List<Treatment> ListAllTreatment();
	
	public Treatment GetTreatmentByID(Long id);
	
	public void SaveData(Treatment treatment);
	
	public void Delete(Long id);
	
	public List<Long> ListAllIllnessID();
	
	public List<Treatment> ListAllTreatmentByAppID(Long appID);
	
	public List<TreatmentSuggestionDTO> ListAllSuggestTreatmentByAppID(Long appID);

}
