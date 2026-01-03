package com.hms.service.impl;

import com.hms.entity.Appointment;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public Appointment createAppointment(Long doctorId,
                                         Long patientId,
                                         LocalDateTime appointmentTime) {

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        appointment.setPatient(patientRepository.findById(patientId).orElseThrow());
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus("BOOKED");

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Long appointmentId,
                                         LocalDateTime appointmentTime,
                                         String status) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow();

        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus(status);

        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
