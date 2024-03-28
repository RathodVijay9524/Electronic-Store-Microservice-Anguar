package com.vijay.authservice.repository;



import com.vijay.authservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, String> {


    // Find a role by its name
    Optional<Role> findByRoleName(String roleName);
}
