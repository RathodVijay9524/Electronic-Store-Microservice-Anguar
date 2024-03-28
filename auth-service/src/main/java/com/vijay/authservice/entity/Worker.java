package com.vijay.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {

	@Id
    private String workerId;
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "workers_roles",
            joinColumns = @JoinColumn(name = "worker_id", referencedColumnName = "workerId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    )
    private Set<Role> roles;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
	private User user;

}
