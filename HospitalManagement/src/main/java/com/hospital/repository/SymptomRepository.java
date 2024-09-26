package com.hospital.repository;

import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Symptom;

public interface SymptomRepository extends CrudRepository<Symptom, Long> {
	
}
