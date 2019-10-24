package com.hospital.service;

import java.util.List;

import com.hospital.model.PrescriptionMedicine;

public interface PrescriptionMedicineService {
	
	public List<PrescriptionMedicine> ListAllPrescriptionMedicine();
	
	public PrescriptionMedicine GetPrescriptionMedicineByID(Long id);
	
	public void SaveData(PrescriptionMedicine pm);
	
	public void Delete(Long id);
	
	public List<Long> ListAllAppID();
	
	public List<PrescriptionMedicine> ListAllPrescriptionMedicineByAppID(Long appID);
	

}
