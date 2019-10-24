package com.hospital.service;

import java.util.List;

import com.hospital.model.Symptom;

public interface SymptomService {
	
	public  List<Symptom> ListAllSymptom();
	
	public Symptom GetSymptomById(long id);
	
	public void SaveData(Symptom symptom);
	
	public void Delete(long id);
		
}
