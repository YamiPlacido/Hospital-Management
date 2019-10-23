package com.hospital.service;

import java.util.List;

import com.hospital.model.Diagnosi;
import com.hospital.model.PrescriptionMedicine;
import com.hospital.model.dto.IllnessSuggestionDTO;

public interface PrescriptionMedicineService {
	
	public List<PrescriptionMedicine> ListAllPrescriptionMedicine();
	
	public PrescriptionMedicine GetPrescriptionMedicineByID(Integer id);
	
	public void SaveData(PrescriptionMedicine pm);
	
	public void Delete(Integer id);
	
	public List<Integer> ListAllAppID();
	
	public List<PrescriptionMedicine> ListAllPrescriptionMedicineByAppID(Integer appID);
	

}
