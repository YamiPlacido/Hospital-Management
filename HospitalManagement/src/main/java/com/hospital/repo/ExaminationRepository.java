package com.hospital.repo;

import com.hospital.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExaminationRepository extends JpaRepository<Examination, Integer> {
    @Query("SELECT e FROM Examination e WHERE e.appointment.appId = :app_id and e.examinationType.id = :examination_type_id")
    public List<Examination> findExaminationByAppointmentAndExaminationType
            (@Param("app_id") int app_id, @Param("examination_type_id") int examination_type_id);
}
