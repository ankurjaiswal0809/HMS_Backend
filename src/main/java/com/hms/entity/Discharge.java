package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.hms.audit.Auditable;

@Entity
@Getter
@Setter
public class Discharge extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime dischargeDate;

    @Column(length = 2000)
    private String summary;
}
