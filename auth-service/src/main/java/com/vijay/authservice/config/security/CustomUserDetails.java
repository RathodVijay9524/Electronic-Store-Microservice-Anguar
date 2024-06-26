package com.vijay.authservice.config.security;

import com.vijay.authservice.entity.Role;
import com.vijay.authservice.entity.User;
import com.vijay.authservice.entity.Worker;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
    private List<Worker> workers;
    private String about;
    private String gender;

     //  Constructor to initialize all fields
  /*  public CustomUserDetails(String id, String name, String username, String password, String email,
                             Set<Role> roles, List<Worker> workers, String about, String gender) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.workers = workers;
        this.about = about;
        this.gender = gender;
    }*/

    // Static factory method to build CustomUserDetails from User entity
    public static CustomUserDetails build(User user) {
        return new CustomUserDetails(
                user.getUserId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRoles(),
                user.getWorkers(),
                user.getAbout(),
                user.getGender()
        );
    }

    // Static factory method to build CustomUserDetails from Worker entity
    public static CustomUserDetails build(Worker worker) {
        return new CustomUserDetails(
                worker.getWorkerId(),
                worker.getName(),
                worker.getUsername(),
                worker.getPassword(),
                worker.getEmail(),
                worker.getRoles(),
                null,
                null,
                null // Workers don't have associated workers, so set it to null
        );
    }

    // Implementing UserDetails interface methods...

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }


    // Implement other UserDetails methods as needed
}

