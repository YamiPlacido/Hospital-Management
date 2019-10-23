package com.hospital.repository;
import org.springframework.data.repository.CrudRepository;
import com.hospital.model.*;

public interface PatientRepository extends CrudRepository<Patient, Long>{

}
