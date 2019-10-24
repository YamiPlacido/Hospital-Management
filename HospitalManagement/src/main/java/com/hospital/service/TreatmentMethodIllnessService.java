package com.hospital.service;

import java.util.List;

import com.hospital.model.TreatmentMethodIllness;


public interface TreatmentMethodIllnessService {
	
	public List<TreatmentMethodIllness> ListAllTreatmentMethod();
	
	public TreatmentMethodIllness GetTreatmentMethodByID(Long id);
	
	public void SaveData(TreatmentMethodIllness treatmentMethod);
	
	public void Delete(Long id);
	
	public List<Long> ListAllIllnessID();

}
