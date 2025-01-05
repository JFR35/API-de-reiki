package com.reikitubienestar.reiki_rest.infraestructure.repositories;


import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.infraestructure.repositories.exception.DatabaseException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaAppointmentRepository implements AppointmentRepository {

    @Autowired
    SpringDataAppointmentRepository repository;

    @PostConstruct
    public void init() {
        // Verifica que el repositorio ha sido inyectado correctamente
        System.out.println("JpaAppointmentRepository initialized: " + (repository != null));
    }

    @Override
    public List<Appointment> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new DatabaseException("Error retrieving appointments", e);
        }
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new DatabaseException("Error finding appointment with ID: " + id, e);
        }
    }

    @Override
    public Appointment save(Appointment appointment) {
        try {
            return repository.save(appointment);
        } catch (Exception e) {
            throw new DatabaseException("Error saving appointment", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseException("Error deleting appointment with ID: " + id, e);
        }
    }

    @Override
    public long countByDateReservationBetween(LocalDateTime startSlot, LocalDateTime endSlot) {
        try {
            return repository.countByDateReservationBetween(startSlot, endSlot);
        } catch (Exception e) {
            throw new DatabaseException("Error counting appointments in the given time slot", e);
        }
    }
}
/*
La clase JpaAppointmentRepository implementa la interfaz del repositorio y maneja las excepciones relacionadas con la base de datos.
 */