package com.hospital.service;

import java.util.List;

import com.hospital.model.Treatment;
import com.hospital.model.dto.TreatmentSuggestionDTO;

public interface TreatmentService {
	
	public List<Treatment> ListAllTreatment();
	
	public Treatment GetTreatmentByID(Integer id);
	
	public void SaveData(Treatment treatment);
	
	public void Delete(Integer id);
	
	public List<Integer> ListAllIllnessID();
	
	public List<Treatment> ListAllTreatmentByAppID(Integer appID);
	
	public List<TreatmentSuggestionDTO> ListAllSuggestTreatmentByAppID(Integer appID);

}
