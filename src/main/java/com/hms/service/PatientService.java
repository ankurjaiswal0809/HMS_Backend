package com.hms.service;

import com.hms.entity.Patient;

import java.util.List;

public interface PatientService {

    Patient addPatient(Patient patient);

    Patient updatePatient(Long patientId, Patient patient);

    void deletePatient(Long patientId);

    List<Patient> getAllPatients();
}
