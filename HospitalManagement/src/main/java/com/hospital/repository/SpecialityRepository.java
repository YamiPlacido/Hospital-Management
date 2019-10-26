package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.model.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    @Query("SELECT s FROM Speciality s WHERE s.position.positionId = :id")
    public List<Speciality> findByPositionId(@Param("id") Long positionId);
}
