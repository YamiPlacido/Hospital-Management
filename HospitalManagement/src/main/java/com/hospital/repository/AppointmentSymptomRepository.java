package com.hospital.repository;

import com.hospital.model.AppointmentSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentSymptomRepository extends JpaRepository<AppointmentSymptom, Long> {
    @Query("SELECT a FROM AppointmentSymptom a WHERE a.appointmentId = :id")
    public List<AppointmentSymptom> findSymptomByAppointmentId(@Param("id") long appointment_id);
}
