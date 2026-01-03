package com.hms.repository;

import com.hms.entity.Bill;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillRepository extends JpaRepository<Bill, Long> {
	
	Optional<Bill> findByPatientId(Long patientId);
	
	 // 🔹 Admin / Staff – total revenue today
    @Query("""
        SELECT COALESCE(SUM(b.amount), 0)
        FROM Bill b
        WHERE b.billDate BETWEEN :start AND :end
    """)
    Double sumRevenueToday(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
	 
	 
	 @Query("""
			    SELECT COALESCE(SUM(b.amount), 0)
			    FROM Bill b
			    WHERE b.billDate BETWEEN :start AND :end
			""")
			Double sumRevenueBetween(
			        @Param("start") LocalDateTime start,
			        @Param("end") LocalDateTime end
			);
	 
	 @Query("""
			    SELECT COALESCE(SUM(b.amount), 0)
			    FROM Bill b
			    WHERE b.billDate BETWEEN :start AND :end
			""")
			Double sumBetween(LocalDateTime start, LocalDateTime end);


	 // 🔹 Doctor – revenue from own appointments
	    @Query("""
	        SELECT COALESCE(SUM(b.amount), 0)
	        FROM Bill b
	        JOIN Appointment a ON a.patient.id = b.patient.id
	        WHERE a.doctor.id = :doctorId
	        AND b.billDate BETWEEN :start AND :end
	    """)
	    Double sumBetweenForDoctor(
	            @Param("doctorId") Long doctorId,
	            @Param("start") LocalDateTime start,
	            @Param("end") LocalDateTime end
	    );



}
