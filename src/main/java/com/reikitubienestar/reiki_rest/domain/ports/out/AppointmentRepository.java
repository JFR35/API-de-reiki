package com.reikitubienestar.reiki_rest.domain.ports.out;


import com.reikitubienestar.reiki_rest.domain.models.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    Appointment save(Appointment appointment);
    void deleteById(Long id);

    long countByDateReservationBetween(LocalDateTime startSlot, LocalDateTime endSlot);
}

/*
Aquí defines las interfaces para interactuar con las tecnologías externas, como bases de datos.
Flujo del caso de uso:
1.El controlador o servicio llama al AppointmentRepository (el puerto) que define las operaciones necesarias.
2.JpaAppointmentRepository (el adaptador de salida) implementa esta interfaz y delega las llamadas a SpringDataAppointmentRepository.
3.SpringDataAppointmentRepository realiza las operaciones CRUD sobre la base de datos usando Spring Data JPA.

Esto sigue el principio de la arquitectura hexagonal, donde el dominio no depende de detalles de implementación como JPA, pero los
detalles de implementación como el repositorio de JPA pueden ser cambiados sin afectar el dominio.
 */