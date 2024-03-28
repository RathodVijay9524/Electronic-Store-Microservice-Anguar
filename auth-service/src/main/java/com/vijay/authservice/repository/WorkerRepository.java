package com.vijay.authservice.repository;

import com.vijay.authservice.entity.Worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;




@Repository
public interface WorkerRepository extends JpaRepository<Worker, String>{

	
	Optional<Worker> findByEmail(String email);

    Optional<Worker> findByUsernameOrEmail(String username, String email);

    Optional<Worker> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
