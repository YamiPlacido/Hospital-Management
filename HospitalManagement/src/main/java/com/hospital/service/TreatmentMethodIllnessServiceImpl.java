package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.TreatmentMethodIllness;
import com.hospital.repository.TreatmentMethodIllnessRepository;

@Component
public class TreatmentMethodIllnessServiceImpl implements TreatmentMethodIllnessService{

	@Autowired
	TreatmentMethodIllnessRepository repo;
	
	@Override
	public List<TreatmentMethodIllness> ListAllTreatmentMethod() {
		List<TreatmentMethodIllness> listTreatmentMethod = (List<TreatmentMethodIllness>) repo.findAll();
		return listTreatmentMethod;
	}

	@Override
	public TreatmentMethodIllness GetTreatmentMethodByID(Integer id) {
		TreatmentMethodIllness tmmi = repo.findById(id).get();
		return tmmi;
	}

	@Override
	public void SaveData(TreatmentMethodIllness treatmentMethod) {
		repo.save(treatmentMethod);
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

}
