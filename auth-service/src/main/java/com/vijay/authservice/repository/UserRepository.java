package com.vijay.authservice.repository;

import com.vijay.authservice.entity.User;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
   
    Optional<User> findByEmailAndPassword(String email,String password);

    List<User> findByNameContaining(String keywords);

    Optional<User> findByName(String username);

}
