package com.hospital.repository;

import org.springframework.data.repository.CrudRepository;

import com.hospital.model.IllnessType;

public interface IllnessTypeRepository extends CrudRepository<IllnessType, Long> {
	
}
