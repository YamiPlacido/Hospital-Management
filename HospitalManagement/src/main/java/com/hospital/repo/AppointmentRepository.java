package com.hospital.repo;

import com.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
//    @Query("SELECT a FROM Appointment a WHERE a.employee.employeeId = :id AND a.date = current_date")
//    public List<Appointment> findAppointmentByDoctorId(@Param("id") int employee_id);
}
