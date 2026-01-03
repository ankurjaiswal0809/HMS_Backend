package com.hms.controller;

import com.hms.dto.AppointmentDTO;
import com.hms.entity.Appointment;
import com.hms.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public Appointment create(@Valid @RequestBody AppointmentDTO dto) {
        return appointmentService.createAppointment(
                dto.getDoctorId(),
                dto.getPatientId(),
                LocalDateTime.parse(dto.getAppointmentTime())
        );
    }

    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id,
                              @RequestBody AppointmentDTO dto) {
        return appointmentService.updateAppointment(
                id,
                LocalDateTime.parse(dto.getAppointmentTime()),
                dto.getStatus()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.getAllAppointments();
    }
}
