package com.hospital.service;
import java.util.List;
 

import com.hospital.model.*;

public interface MedicineService {  

	 public List<Medicine> getAllMedicine();
	 
	 public List<Patient> getAllPatient();
	 
	 public Medicine getMedicineById(long appId);
	 
	 public void saveOrUpdate(Medicine medicine);

	public void disableMedicine(Long medicineID);
	 
}
