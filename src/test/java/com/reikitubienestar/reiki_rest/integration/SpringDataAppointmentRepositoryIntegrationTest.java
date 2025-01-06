package com.reikitubienestar.reiki_rest.integration;

import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.infraestructure.repositories.SpringDataAppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SpringDataAppointmentRepositoryIntegrationTest {

    @Autowired
    private SpringDataAppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        // Crear y guardar una cita
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Pérez");
        appointment.setEmail("juan.perez@example.com");
        appointment.setTlph("+34123456789");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Verificar que la cita ha sido guardada y puede ser encontrada por ID
        Optional<Appointment> foundAppointment = appointmentRepository.findById(savedAppointment.getId());
        assertThat(foundAppointment).isPresent();
        assertThat(foundAppointment.get().getFirstName()).isEqualTo("Juan");
    }

    @Test
    void testFindAll() {
        // Crear y guardar varias citas
        Appointment appointment1 = new Appointment();
        appointment1.setFirstName("Juan");
        appointment1.setLastName("Pérez");
        appointment1.setEmail("juan.perez@example.com");
        appointment1.setTlph("+34123456789");
        appointment1.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment appointment2 = new Appointment();
        appointment2.setFirstName("Ana");
        appointment2.setLastName("García");
        appointment2.setEmail("ana.garcia@example.com");
        appointment2.setTlph("+34123456790");
        appointment2.setDateReservation(LocalDateTime.now().plusDays(2));

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        // Verificar que todas las citas pueden ser encontradas
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        assertThat(appointments).hasSize(2);
    }

    @Test
    void testDeleteById() {
        // Crear y guardar una cita
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Pérez");
        appointment.setEmail("juan.perez@example.com");
        appointment.setTlph("+34123456789");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Verificar que la cita ha sido guardada
        Optional<Appointment> existBeforeDelete = appointmentRepository.findById(savedAppointment.getId());
        assertThat(existBeforeDelete).isPresent();

        // Eliminar la cita
        appointmentRepository.deleteById(savedAppointment.getId());

        // Verificar que la cita ha sido eliminada
        Optional<Appointment> existAfterDelete = appointmentRepository.findById(savedAppointment.getId());
        assertThat(existAfterDelete).isNotPresent();
    }

    @Test
    void testCountByDateReservationBetween() {
        // Crear y guardar citas
        Appointment appointment1 = new Appointment();
        appointment1.setFirstName("Juan");
        appointment1.setLastName("Pérez");
        appointment1.setEmail("juan.perez@example.com");
        appointment1.setTlph("+34123456789");
        appointment1.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment appointment2 = new Appointment();
        appointment2.setFirstName("Ana");
        appointment2.setLastName("García");
        appointment2.setEmail("ana.garcia@example.com");
        appointment2.setTlph("+34123456790");
        appointment2.setDateReservation(LocalDateTime.now().plusDays(1).plusHours(1));

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        // Contar el número de citas en un intervalo de tiempo
        LocalDateTime startSlot = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endSlot = startSlot.plusDays(1);
        long count = appointmentRepository.countByDateReservationBetween(startSlot, endSlot);

        assertThat(count).isEqualTo(2);
    }
}
