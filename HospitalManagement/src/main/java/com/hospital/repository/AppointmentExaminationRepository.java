package com.hospital.repository;

import com.hospital.model.AppointmentExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppointmentExaminationRepository extends JpaRepository<AppointmentExamination, Long> {
    @Query("SELECT a FROM AppointmentExamination a WHERE a.appointmentId = :id")
    public List<AppointmentExamination> findExaminationByAppointmentId(@Param("id") long appointment_id);
}
