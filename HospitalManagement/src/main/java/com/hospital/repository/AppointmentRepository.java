package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

	@Query(value = "SELECT * FROM appointment a WHERE a.employee_id = ?1", nativeQuery = true)
	public List<Appointment> GetAllAppByDoctorID(Integer id);
	
	@Query(value = "SELECT a.patient_id FROM appointment a WHERE a.app_id = ?1", nativeQuery = true)
	public Integer GetPatientIDByAppID (Integer id);
}
