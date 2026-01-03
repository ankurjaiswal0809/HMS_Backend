package com.hms.entity;

import com.hms.audit.Auditable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private int age;

    private String gender;

    private String phone;

    private String email;

    private String address;
}
