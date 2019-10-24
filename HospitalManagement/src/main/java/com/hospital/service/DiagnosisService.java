package com.hospital.service;

import java.util.List;

import com.hospital.model.Diagnosi;
import com.hospital.model.dto.IllnessSuggestionDTO;

public interface DiagnosisService {
	
	public List<Diagnosi> ListAllDianosis();
	
	public Diagnosi GetDiagnosisByID(Long id);
	
	public void SaveData(Diagnosi diagnosis);
	
	public void Delete(Long id);
	
	public List<Long> ListAllAppID();
	
	public List<Diagnosi> ListAllDiagnosisByAppID(Long appID);
	
	public List<IllnessSuggestionDTO> ListAllSuggestIllnessByExaminationResult(Long IllnessID);

}
