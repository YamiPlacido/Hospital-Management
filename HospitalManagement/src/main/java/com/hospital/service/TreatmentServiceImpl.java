package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Treatment;
import com.hospital.model.dto.TreatmentSuggestionDTO;
import com.hospital.repository.TreatmentRepository;

@Component
public class TreatmentServiceImpl implements TreatmentService{

	@Autowired
	TreatmentRepository repo;
	
	@Override
	public List<Treatment> ListAllTreatment() {
		List<Treatment> listTreatmentMethod = (List<Treatment>) repo.findAll();
		return listTreatmentMethod;
	}

	@Override
	public Treatment GetTreatmentByID(Integer id) {
		Treatment tm = repo.findById(id).get();
		return tm;
	}

	@Override
	public void SaveData(Treatment treatment) {
		repo.save(treatment);
	}

	@Override
	public void Delete(Integer id) {
		repo.deleteById(id);		
	}

	@Override
	public List<Integer> ListAllIllnessID() {
		List<Integer> listAllIllnessID = repo.ListAllIllnessID();
		return listAllIllnessID;
	}

	@Override
	public List<Treatment> ListAllTreatmentByAppID(Integer appID) {
		List<Treatment> treatments = repo.ListAllTreatmentByAppID(appID);
		return treatments;
	}

	@Override
	public List<TreatmentSuggestionDTO> ListAllSuggestTreatmentByAppID(Integer appID) {
		List<TreatmentSuggestionDTO> treatsug = repo.ListAllSuggestTreatmentByAppID(appID);
		return treatsug;
	}


}
