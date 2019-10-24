package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Treatment;
import com.hospital.model.TreatmentMethod;
import com.hospital.model.dto.TreatmentSuggestionDTO;
import com.hospital.repository.TreatmentMethodRepository;
import com.hospital.repository.TreatmentRepository;

@Component
public class TreatmentMethodServiceImpl implements TreatmentMethodService{

	@Autowired
	TreatmentMethodRepository repo;
	
	@Override
	public List<TreatmentMethod> ListAllTreatmentMethod() {
		List<TreatmentMethod> listTreatmentMethod = (List<TreatmentMethod>) repo.findAll();
		return listTreatmentMethod;
	}


	@Override
	public void SaveData(TreatmentMethod tm) {
		repo.save(tm);
	}

	@Override
	public void Delete(Long id) {
		repo.deleteById(id);		
	}

	@Override
	public TreatmentMethod GetTreatmentMethodByID(Long id) {
		TreatmentMethod tm = repo.findById(id).get();
		return tm;
	}



}
