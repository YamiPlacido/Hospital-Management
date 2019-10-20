package com.hospital.repo;

import com.hospital.entity.AppointmentExamination;
import com.hospital.entity.AppointmentSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppointmentExaminationRepository extends JpaRepository<AppointmentExamination, Integer> {
    @Query("SELECT a FROM AppointmentExamination a WHERE a.appointmentId = :id")
    public List<AppointmentExamination> findExaminationByAppointmentId(@Param("id") int appointment_id);
    @Modifying
    @Query(value = "insert into examination (app_id, examination_type_id, examinator_id, patient_id) " +
            "values (:app_id, :examination_type_id, :examinator_id, :patient_id)",
            nativeQuery = true)
    @Transactional
    void insertExamination(@Param("app_id") int app_id, @Param("examination_type_id") Integer examination_type_id,
                    @Param("examinator_id") Integer examinator_id, @Param("patient_id") int patient_id);

}
