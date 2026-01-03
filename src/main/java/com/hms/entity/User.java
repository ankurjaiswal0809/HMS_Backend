package com.hms.entity;

import com.hms.audit.Auditable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // ADMIN, DOCTOR, STAFF, PHARMACIST, USER
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean enabled = true;   
    
 // 👇 Soft delete flag
    private boolean deleted = false;
}
