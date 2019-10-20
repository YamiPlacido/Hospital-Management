package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.TreatmentMethodIllness;

public interface TreatmentMethodIllnessRepository extends CrudRepository<TreatmentMethodIllness, Integer> {
	
	@Query(value = "SELECT illness_id FROM illness", nativeQuery = true)
	public List<Integer> ListAllIllnessID();

}
