package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.model.Speciality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
    @Query("SELECT s FROM Speciality s WHERE s.position.positionId = :id")
    public List<Speciality> findByPositionId(@Param("id") int position_id);
}
