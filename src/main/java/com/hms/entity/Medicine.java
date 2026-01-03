package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import com.hms.audit.Auditable;

@Entity
@Getter
@Setter
public class Medicine extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String batchNo;

    private LocalDate expiryDate;

    private Double price;

    private Integer stock;
}
