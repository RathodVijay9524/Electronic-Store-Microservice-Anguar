package com.vijay.authservice;

import com.vijay.authservice.entity.Role;
import com.vijay.authservice.entity.User;
import com.vijay.authservice.entity.Worker;
import com.vijay.authservice.repository.RoleRepo;
import com.vijay.authservice.repository.UserRepository;
import com.vijay.authservice.repository.WorkerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final RoleRepo roleRepository;

    public AuthServiceApplication(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                  WorkerRepository workerRepository, RoleRepo roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @PostConstruct
    protected void init() {
        if (userRepository.count() == 0 && workerRepository.count() == 0 && roleRepository.count() == 0) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(createRole("ROLE_ADMIN"));

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(createRole("ROLE_SUPER_USER"));

            Set<Role> normalRoles = new HashSet<>();
            normalRoles.add(createRole("ROLE_NORMAL"));

            Set<Role> workerRoles = new HashSet<>();
            workerRoles.add(createRole("ROLE_WORKER"));

            // Create users
            User admin = User.builder()
                    .name("Vijay Rathod")
                    .userId(UUID.randomUUID().toString())
                    .email("admin@gmail.com")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(adminRoles)
                    .build();

            User user = User.builder()
                    .name("Ajay Sharma")
                    .userId(UUID.randomUUID().toString())
                    .email("user@gmail.com")
                    .username("superuser")
                    .password(passwordEncoder.encode("superuser"))
                    .roles(userRoles)
                    .build();

            User normalUser = User.builder()
                    .name("Kiran More")
                    .userId(UUID.randomUUID().toString())
                    .email("normal@gmail.com")
                    .username("normal")
                    .password(passwordEncoder.encode("normal"))
                    .roles(normalRoles)
                    .build();

            // Save users
            userRepository.saveAll(Arrays.asList(admin, user, normalUser));

            Worker worker = Worker.builder()
                    .name("Salman Khan")
                    .workerId(UUID.randomUUID().toString())
                    .email("worker@gmail.com")
                    .username("worker")
                    .password(passwordEncoder.encode("worker"))
                    .roles(workerRoles)
                    .user(admin)
                    .build();

            workerRepository.save(worker);
        }
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setRoleName(roleName);
        return role;
    }
}