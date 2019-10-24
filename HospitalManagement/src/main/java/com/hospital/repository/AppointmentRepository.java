package com.hospital.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>,AppointmentBookRepository{

	@Query(value = "SELECT a FROM Appointment a WHERE a.employee.employeeId = ?1")
	public List<Appointment> GetAllAppByDoctorID(Long id);
	
	@Query(value = "SELECT a.patient_id FROM appointment a WHERE a.app_id = ?1", nativeQuery = true)
	public Long GetPatientIDByAppID (Long id);
	
}
