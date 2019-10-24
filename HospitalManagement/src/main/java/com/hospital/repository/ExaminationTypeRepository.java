package com.hospital.repository;

import com.hospital.model.ExaminationType;
import com.hospital.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.repository.CrudRepository;

public interface ExaminationTypeRepository extends JpaRepository<ExaminationType, Long> {

}
