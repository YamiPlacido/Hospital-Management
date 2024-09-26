package com.hospital.service;

import java.util.List;

import com.hospital.model.TreatmentMethodIllness;


public interface TreatmentMethodIllnessService {
	
	public List<TreatmentMethodIllness> ListAllTreatmentMethod();
	
	public TreatmentMethodIllness GetTreatmentMethodByID(Integer id);
	
	public void SaveData(TreatmentMethodIllness treatmentMethod);
	
	public void Delete(Integer id);
	
	public List<Integer> ListAllIllnessID();

}
