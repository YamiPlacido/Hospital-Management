package com.hospital.service;

import java.util.List;

import com.hospital.model.Diagnosi;
import com.hospital.model.dto.IllnessSuggestionDTO;

public interface DiagnosisService {
	
	public List<Diagnosi> ListAllDianosis();
	
	public Diagnosi GetDiagnosisByID(Integer id);
	
	public void SaveData(Diagnosi diagnosis);
	
	public void Delete(Integer id);
	
	public List<Integer> ListAllAppID();
	
	public List<Diagnosi> ListAllDiagnosisByAppID(Integer appID);
	
	public List<IllnessSuggestionDTO> ListAllSuggestIllnessByExaminationResult(Integer IllnessID);

}
