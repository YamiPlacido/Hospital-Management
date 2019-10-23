package com.hospital.repository;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>,AppointmentBookRepository{


	
}
