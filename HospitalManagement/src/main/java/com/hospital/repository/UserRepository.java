package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.model.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
	@Query("SELECT u FROM Users u WHERE u.userName = :username")
    public List<Users> findUserid(@Param("username") String username);
}
