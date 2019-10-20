package com.hospital.repo;

import com.hospital.entity.Appointment;
import com.hospital.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
//    @Query("SELECT a FROM Appointment a WHERE a.employee.employeeId = :id AND a.date = current_date")
//    public List<Appointment> findAppointmentByDoctorId(@Param("id") int employee_id);
}
