package com.hms.service;

import com.hms.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Long doctorId,
                                  Long patientId,
                                  LocalDateTime appointmentTime);

    Appointment updateAppointment(Long appointmentId,
                                  LocalDateTime appointmentTime,
                                  String status);

    void deleteAppointment(Long appointmentId);

    List<Appointment> getAllAppointments();
}
