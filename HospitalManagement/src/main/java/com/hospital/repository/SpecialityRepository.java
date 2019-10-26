package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.model.Speciality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    @Query("SELECT s FROM Speciality s WHERE s.position.positionId = :id")
<<<<<<< Updated upstream
    public List<Speciality> findByPositionId(@Param("id") Long position_id);
=======
    public List<Speciality> findByPositionId(@Param("id") Long positionId);
>>>>>>> Stashed changes
}
