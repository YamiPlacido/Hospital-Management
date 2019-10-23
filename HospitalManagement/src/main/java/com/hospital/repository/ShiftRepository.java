package com.hospital.repository;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Shift;

public interface ShiftRepository extends CrudRepository<Shift, Long>{ 

}
