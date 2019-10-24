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
	public Diagnosi GetDiagnosisByID(Long id) {
		Diagnosi diag = DiagRepo.findById(id).get();
		return diag;
	}

	@Override
	public void SaveData(Diagnosi diagnosis) {
		DiagRepo.save(diagnosis);		
	}

	@Override
	public void Delete(Long id) {
		DiagRepo.deleteById(id);		
	}

	@Override
	public List<Long> ListAllAppID() {
		List<Long> appID = DiagRepo.ListAllAppID();
		return appID;
	}

	@Override
	public List<Diagnosi> ListAllDiagnosisByAppID(Long appID) {
		List<Diagnosi> diag = DiagRepo.ListAllDiagnosisByAppID(appID);
		return diag;
	}

	@Override
	public List<IllnessSuggestionDTO> ListAllSuggestIllnessByExaminationResult(Long PatientID) {
		List<IllnessSuggestionDTO> illsug = DiagRepo.ListAllSuggestIllnessByExaminationResult(PatientID);
		return illsug;
	}

	

}
