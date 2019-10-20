package com.hospital.repo;

import com.hospital.entity.Appointment;
import com.hospital.entity.AppointmentSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentSymptomRepository extends JpaRepository<AppointmentSymptom, Integer> {
    @Query("SELECT a FROM AppointmentSymptom a WHERE a.appointmentId = :id")
    public List<AppointmentSymptom> findSymptomByAppointmentId(@Param("id") int appointment_id);
}
