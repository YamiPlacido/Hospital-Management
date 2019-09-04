package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Illness;
import com.hospital.model.IllnessType;

public interface IllnessRepository extends CrudRepository<Illness, Long> {
	
	@Query(value = "select t from IllnessType t")
	public List<IllnessType> ListAllIllnessType();
}
