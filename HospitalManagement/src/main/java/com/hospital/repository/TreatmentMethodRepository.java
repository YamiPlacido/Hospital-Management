package com.hospital.repository;


import org.springframework.data.repository.CrudRepository;

import com.hospital.model.TreatmentMethod;

public interface TreatmentMethodRepository extends CrudRepository<TreatmentMethod, Long> {
	
}
