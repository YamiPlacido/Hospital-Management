package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.PrescriptionMedicine;
import com.hospital.repository.PrescriptionMedicineRepository;

@Component
public class PrescriptionMedicineImpl implements PrescriptionMedicineService {

	@Autowired
	PrescriptionMedicineRepository PresRepo;

	@Override
	public List<PrescriptionMedicine> ListAllPrescriptionMedicine() {
		List<PrescriptionMedicine> listPres = (List<PrescriptionMedicine>) PresRepo.findAll();	
		return listPres;
	}

	@Override
	public PrescriptionMedicine GetPrescriptionMedicineByID(Long id) {
		PrescriptionMedicine pres = PresRepo.findById(id).get();
		return pres;
	}

	@Override
	public void SaveData(PrescriptionMedicine pm) {
		PresRepo.save(pm);		
		
	}

	@Override
	public void Delete(Long id) {
		PresRepo.deleteById(id);		
		
	}

	@Override
	public List<Long> ListAllAppID() {
		List<Long> appIds = PresRepo.ListAllAppID();
		return appIds;
	}

	@Override
	public List<PrescriptionMedicine> ListAllPrescriptionMedicineByAppID(Long appID) {
		List<PrescriptionMedicine> pm = PresRepo.ListAllPrescriptionMedicineByAppID(appID);
		return pm;
	}

}
