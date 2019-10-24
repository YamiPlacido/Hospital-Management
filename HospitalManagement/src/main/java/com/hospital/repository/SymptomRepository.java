package com.hospital.repository;

import com.hospital.model.Position;
import com.hospital.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.repository.CrudRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

}
