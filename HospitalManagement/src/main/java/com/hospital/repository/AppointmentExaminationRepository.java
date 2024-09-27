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
    @Query(value = "SELECT et.id FROM appointment_examination a join Examination e " +
            "on a.ex_id = e.ex_id " +
            "join examination_type et " +
            "on e.examination_type_id = et.id " +
            "WHERE a.app_id = :id",
            nativeQuery = true)
    public List<Long> findExaminationTypeIdByAppointmentId(@Param("id") long appointment_id);
    @Query(value = "SELECT et.id FROM appointment_examination a join Examination e " +
            "on a.ex_id = e.ex_id " +
            "join examination_type et " +
            "on e.examination_type_id = et.id " +
            "WHERE a.app_id = :id and e.stage = 'CREATED'",
            nativeQuery = true)
    public List<Long> findExaminationTypeIdStatusCreatedByAppointmentId(@Param("id") long appointment_id);
    @Query(value = "SELECT a.id FROM appointment_examination a join Examination e " +
            "on a.ex_id = e.ex_id " +
            "join examination_type et " +
            "on e.examination_type_id = et.id " +
            "WHERE a.app_id = :appointment_id and et.id = :examination_type_id",
            nativeQuery = true)
    public List<Long> findIdByAppointmentIdAndExaminationTypeId(@Param("appointment_id") long appointment_id,
                                                              @Param("examination_type_id") long examination_type_id);
}
