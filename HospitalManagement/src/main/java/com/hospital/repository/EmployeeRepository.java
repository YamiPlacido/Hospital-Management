package com.hospital.repository;

import com.hospital.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    @Query("SELECT e FROM Employee e WHERE e.status = 1")
    public List<Employee> findEmployeesActive(@Param("id") int speciality_id);
    @Query("SELECT s FROM Symptom s")
    public Set<Symptom> findAllSymptom();
    @Query("SELECT et FROM ExaminationType et")
    public Set<ExaminationType> findAllExamination();
    @Query("SELECT e FROM Employee e WHERE e.specialityId = :id")
    public List<Employee> findEmployeesBySpeciality(@Param("id") int speciality_id);
    @Query("SELECT e FROM ExaminationType et LEFT JOIN Employee e ON et.specialityId = e.specialityId" +
            " WHERE et.id = :id")
    public List<Employee> findExaminatorsByExaminationType(@Param("id") long examination_type_id);
    @Query("SELECT s FROM Symptom s where s.symptomTypeId = 1")
    public List<Symptom> fillSymptomTypeOne();
    @Query("SELECT s FROM Symptom s where s.symptomTypeId = 2")
    public List<Symptom> fillSymptomTypeTwo();
    @Query("SELECT et.id FROM SymptomExaminationType se " +
            "left join ExaminationType et " +
            "on se.examinationTypeId = et.id " +
            "WHERE se.symptomId = :id")
    public List<Long> findExaminationTypeBySymptomId(@Param("id") long symptom_id);
    @Query(value = "SELECT a.app_id FROM Appointment a where a.patient_id = :patient_id " +
            "and a.stage = 'FINISHED'",nativeQuery = true)
    public List<Long> findAllFinishedAppByPatientId(@Param("patient_id") long patient_id);
    @Query(value = "SELECT e.ex_id FROM Examination e where e.app_id = :app_id " +
            "and e.stage = 'FINISHED'",nativeQuery = true)
    public List<Long> findExaminationByAppId(@Param("app_id") long app_id);
    @Query(value = "SELECT s.symptom_id FROM appointment_symptom s where s.app_id = :app_id",nativeQuery = true)
    public List<Long> findSymptomIdByAppId(@Param("app_id") long app_id);
}
