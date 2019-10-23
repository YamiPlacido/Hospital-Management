package com.hospital.repository;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Medicine;

public interface MedicineRepository extends CrudRepository<Medicine, Long>{ 

}
