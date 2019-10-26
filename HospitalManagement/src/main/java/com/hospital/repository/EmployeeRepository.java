package com.hospital.repository;

import com.hospital.model.ExaminationType;
import com.hospital.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.model.Employee;
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
}
