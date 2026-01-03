package com.hms.service;

import com.hms.entity.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor addDoctor(Doctor doctor);

    Doctor updateDoctor(Long doctorId, Doctor doctor);

    void deleteDoctor(Long doctorId);

    List<Doctor> getAllDoctors();
}
