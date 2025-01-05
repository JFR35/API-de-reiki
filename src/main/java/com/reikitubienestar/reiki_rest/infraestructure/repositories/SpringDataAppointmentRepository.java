package com.reikitubienestar.reiki_rest.infraestructure.repositories;

import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SpringDataAppointmentRepository extends JpaRepository<Appointment, Long> {
    long countByDateReservationBetween(LocalDateTime start, LocalDateTime end);
}
/*
Este repositorio extiende JpaRepository y proporciona métodos para interactuar con la base de datos.
 */
