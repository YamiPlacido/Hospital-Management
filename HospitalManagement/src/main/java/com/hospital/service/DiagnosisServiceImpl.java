package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Diagnosi;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.repository.DiagnosisRepository;

@Component
public class DiagnosisServiceImpl implements DiagnosisService {

	@Autowired
	DiagnosisRepository DiagRepo;
	
	@Override
	public List<Diagnosi> ListAllDianosis() {
		List<Diagnosi> listDiag = (List<Diagnosi>) DiagRepo.findAll();	
		return listDiag;
	}

	@Override
	public Diagnosi GetDiagnosisByID(Integer id) {
		Diagnosi diag = DiagRepo.findById(id).get();
		return diag;
	}

	@Override
	public void SaveData(Diagnosi diagnosis) {
		DiagRepo.save(diagnosis);		
	}

	@Override
	public void Delete(Integer id) {
		DiagRepo.deleteById(id);		
	}

	@Override
	public List<Integer> ListAllAppID() {
		List<Integer> appID = DiagRepo.ListAllAppID();
		return appID;
	}

	@Override
	public List<Diagnosi> ListAllDiagnosisByAppID(Integer appID) {
		List<Diagnosi> diag = DiagRepo.ListAllDiagnosisByAppID(appID);
		return diag;
	}

	@Override
	public List<IllnessSuggestionDTO> ListAllSuggestIllnessByExaminationResult(Integer PatientID) {
		List<IllnessSuggestionDTO> illsug = DiagRepo.ListAllSuggestIllnessByExaminationResult(PatientID);
		return illsug;
	}

	

}
