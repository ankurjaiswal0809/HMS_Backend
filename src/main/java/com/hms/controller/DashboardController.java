package com.hms.controller;

import com.hms.config.UserPrincipal;
import com.hms.dto.DashboardStats;
import com.hms.repository.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;
    private final BillRepository billRepo;
    private final MedicineRepository medicineRepo;

    public DashboardController(
            DoctorRepository doctorRepo,
            PatientRepository patientRepo,
            AppointmentRepository appointmentRepo,
            BillRepository billRepo,
            MedicineRepository medicineRepo
    ) {
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
        this.billRepo = billRepo;
        this.medicineRepo = medicineRepo;
    }

    // 🔹 KPI STATS
    @GetMapping("/stats")
    public DashboardStats stats() {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        return new DashboardStats(
                doctorRepo.count(),
                patientRepo.count(),
                appointmentRepo.countAppointmentsToday(start, end),
                medicineRepo.count(),
                billRepo.sumRevenueToday(start, end)
        );
    }

    // 🔹 APPOINTMENTS CHART (LAST 7 DAYS)
    @GetMapping("/appointments/chart")
    public Map<String, Object> appointmentChart() {

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.atTime(23, 59, 59);

            labels.add(day.getDayOfWeek().name());
            data.add(appointmentRepo.countAppointmentsToday(start, end));
        }

        return Map.of(
                "labels", labels,
                "data", data
        );
    }

    // 🔹 REVENUE CHART (LAST 7 DAYS)
    @GetMapping("/revenue/chart")
    public Map<String, Object> revenueChart() {

        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.atTime(23, 59, 59);

            labels.add(day.getDayOfWeek().name());
            data.add(billRepo.sumRevenueToday(start, end));
        }

        return Map.of(
                "labels", labels,
                "data", data
        );
    }
    
    
 // 🔹 MONTHLY APPOINTMENTS (LAST 12 MONTHS)
//    @GetMapping("/appointments/monthly")
//    public Map<String, Object> monthlyAppointments() {
//
//        List<String> labels = new ArrayList<>();
//        List<Long> data = new ArrayList<>();
//
//        for (int i = 11; i >= 0; i--) {
//            LocalDate month = LocalDate.now().minusMonths(i);
//            LocalDateTime start = month.withDayOfMonth(1).atStartOfDay();
//            LocalDateTime end = month.withDayOfMonth(month.lengthOfMonth())
//                    .atTime(23, 59, 59);
//
//            labels.add(month.getMonth().name().substring(0, 3));
//            data.add(appointmentRepo.countAppointmentsBetween(start, end));
//        }
//
//        return Map.of("labels", labels, "data", data);
//    }


    // 🔹 MONTHLY REVENUE (LAST 12 MONTHS)
//    @GetMapping("/revenue/monthly")
//    public Map<String, Object> monthlyRevenue() {
//
//        List<String> labels = new ArrayList<>();
//        List<Double> data = new ArrayList<>();
//
//        for (int i = 11; i >= 0; i--) {
//            LocalDate month = LocalDate.now().minusMonths(i);
//            LocalDateTime start = month.withDayOfMonth(1).atStartOfDay();
//            LocalDateTime end = month.withDayOfMonth(month.lengthOfMonth())
//                    .atTime(23, 59, 59);
//
//            labels.add(month.getMonth().name().substring(0, 3));
//            data.add(billRepo.sumRevenueBetween(start, end));
//        }
//
//        return Map.of("labels", labels, "data", data);
//    }
    
    private String getRole() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }

    private Long getDoctorId() {
        return ((UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getDoctorId();
    }
    
    @GetMapping("/appointments/monthly")
    public Map<String, Object> monthlyAppointments() {

        String role = getRole();
        Long doctorId = getDoctorId();

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();

        for (int i = 11; i >= 0; i--) {
            LocalDate month = LocalDate.now().minusMonths(i);
            LocalDateTime start = month.withDayOfMonth(1).atStartOfDay();
            LocalDateTime end = month.withDayOfMonth(month.lengthOfMonth())
                    .atTime(23, 59, 59);

            labels.add(month.getMonth().name().substring(0, 3));

            if (role.equals("ROLE_DOCTOR")) {
                data.add(appointmentRepo.countBetweenForDoctor(doctorId, start, end));
            } else {
                data.add(appointmentRepo.countBetween(start, end));
            }
        }

        return Map.of("labels", labels, "data", data);
    }
    
    @GetMapping("/revenue/monthly")
    public Map<String, Object> monthlyRevenue() {

        String role = getRole();
        Long doctorId = getDoctorId();

        if (role.equals("ROLE_STAFF")) {
            return Map.of("labels", List.of(), "data", List.of());
        }

        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        for (int i = 11; i >= 0; i--) {
            LocalDate month = LocalDate.now().minusMonths(i);
            LocalDateTime start = month.withDayOfMonth(1).atStartOfDay();
            LocalDateTime end = month.withDayOfMonth(month.lengthOfMonth())
                    .atTime(23, 59, 59);

            labels.add(month.getMonth().name().substring(0, 3));

            if (role.equals("ROLE_DOCTOR")) {
                data.add(billRepo.sumBetweenForDoctor(doctorId, start, end));
            } else {
                data.add(billRepo.sumBetween(start, end));
            }
        }

        return Map.of("labels", labels, "data", data);
    }
    
    



}
