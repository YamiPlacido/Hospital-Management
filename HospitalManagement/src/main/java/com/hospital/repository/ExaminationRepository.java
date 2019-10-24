package com.hospital.repository;

import com.hospital.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    @Query("SELECT e FROM Examination e WHERE e.appointment.appId = :app_id and e.examinationType.id = :examination_type_id")
    public List<Examination> findExaminationByAppointmentAndExaminationType
            (@Param("app_id") long app_id, @Param("examination_type_id") long examination_type_id);
    @Modifying
    @Query(value = "insert into examination (app_id, examination_type_id, examinator_id, patient_id) " +
            "values (:app_id, :examination_type_id, :examinator_id, :patient_id)",
            nativeQuery = true)
    @Transactional
    void insertExamination(@Param("app_id") long app_id, @Param("examination_type_id") long examination_type_id,
                           @Param("examinator_id") long examinator_id, @Param("patient_id") long patient_id);
}
