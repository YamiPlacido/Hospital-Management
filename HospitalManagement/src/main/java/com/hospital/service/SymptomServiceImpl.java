package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hospital.model.Symptom;
import com.hospital.repository.SymptomRepository;

public class SymptomServiceImpl implements SymptomService {

	@Autowired
	SymptomRepository symptomRepo;
	
	@Override
	public List<Symptom> ListAllSymptom() {
		List<Symptom> symptom = (List<Symptom>) symptomRepo.findAll();
		return null;
	}

	@Override
	public Symptom GetSymptomById(long id) {
		Symptom symptom = symptomRepo.findById(id).get();
		return symptom;
	}

	@Override
	public void SaveData(Symptom symptom) {
		symptomRepo.save(symptom);
	}

	@Override
	public void Delete(long id) {
		symptomRepo.deleteById(id);
	}

}
