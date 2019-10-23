package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.model.Medicine;
import com.hospital.model.Patient;
import com.hospital.repository.MedicineRepository;
import com.hospital.repository.PatientRepository;

@Service
@Transactional(readOnly = false)
@Repository
@Component
public class MedicineServiceImpl implements MedicineService {
   
	 
	 @Autowired
	 MedicineRepository medicineRepository;
	 
	 @Autowired
	 PatientRepository patientRepository;
	 
	 @Override
	 public List<Patient> getAllPatient() {
		 List<Patient> patientList = (List<Patient>) patientRepository.findAll();
		 return patientList;
	 }


	 @Override
	 public List<Medicine> getAllMedicine() {
		 List<Medicine> medicineList = (List<Medicine>) medicineRepository.findAll();
		 return medicineList;
	 }
	 
	
	 @Override
	 public Medicine getMedicineById(long appId) {
	  return medicineRepository.findById(appId).get();
	 }

	 @Override
	 public void saveOrUpdate(Medicine medicine) {
		 medicineRepository.save(medicine);
	 }

	 public void disableMedicine(Long medicineID) {
		 Medicine medicine = medicineRepository.findById(medicineID).get();
		 medicine.setStatus(false);
		 medicineRepository.save(medicine);
	 }

}


