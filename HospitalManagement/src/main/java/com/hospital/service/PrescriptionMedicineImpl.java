package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Diagnosi;
import com.hospital.model.PrescriptionMedicine;
import com.hospital.model.dto.IllnessSuggestionDTO;
import com.hospital.repository.DiagnosisRepository;
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
	public PrescriptionMedicine GetPrescriptionMedicineByID(Integer id) {
		PrescriptionMedicine pres = PresRepo.findById(id).get();
		return pres;
	}

	@Override
	public void SaveData(PrescriptionMedicine pm) {
		PresRepo.save(pm);		
		
	}

	@Override
	public void Delete(Integer id) {
		PresRepo.deleteById(id);		
		
	}

	@Override
	public List<Integer> ListAllAppID() {
		List<Integer> appIds = PresRepo.ListAllAppID();
		return appIds;
	}

	@Override
	public List<PrescriptionMedicine> ListAllPrescriptionMedicineByAppID(Integer appID) {
		List<PrescriptionMedicine> pm = PresRepo.ListAllPrescriptionMedicineByAppID(appID);
		return pm;
	}
	


	

}
