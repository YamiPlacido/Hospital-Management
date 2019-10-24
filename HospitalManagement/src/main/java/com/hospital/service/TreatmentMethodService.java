package com.hospital.service;

import java.util.List;

import com.hospital.model.Treatment;
import com.hospital.model.TreatmentMethod;
import com.hospital.model.dto.TreatmentSuggestionDTO;

public interface TreatmentMethodService {
	
	public List<TreatmentMethod> ListAllTreatmentMethod();
	
	public TreatmentMethod GetTreatmentMethodByID(Long id);
	
	public void SaveData(TreatmentMethod tm);
	
	public void Delete(Long id);
	
}
