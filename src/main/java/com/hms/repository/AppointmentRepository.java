package com.hms.repository;

import com.hms.entity.Appointment;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query("""
	        SELECT COUNT(a)
	        FROM Appointment a
	        WHERE a.appointmentTime BETWEEN :start AND :end
	    """)
	    long countAppointmentsToday(
	            @Param("start") LocalDateTime start,
	            @Param("end") LocalDateTime end
	    );
	
	
	@Query("""
		    SELECT COUNT(a)
		    FROM Appointment a
		    WHERE a.appointmentTime BETWEEN :start AND :end
		""")
		Long countAppointmentsBetween(
		        @Param("start") LocalDateTime start,
		        @Param("end") LocalDateTime end
		);

	
	@Query("""
		    SELECT COUNT(a)
		    FROM Appointment a
		    WHERE a.appointmentTime BETWEEN :start AND :end
		""")
		Long countBetween(LocalDateTime start, LocalDateTime end);


		@Query("""
		    SELECT COUNT(a)
		    FROM Appointment a
		    WHERE a.doctor.id = :doctorId
		      AND a.appointmentTime BETWEEN :start AND :end
		""")
		Long countBetweenForDoctor(
		        Long doctorId,
		        LocalDateTime start,
		        LocalDateTime end
		);


}
