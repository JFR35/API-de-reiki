package com.reikitubienestar.reiki_rest.unit.infraestructure.repositories;

import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.out.EmailService;
import com.reikitubienestar.reiki_rest.infraestructure.repositories.JpaAppointmentRepository;
import com.reikitubienestar.reiki_rest.infraestructure.repositories.SpringDataAppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.reikitubienestar.reiki_rest")
public class JpaAppointmentRepositoryTest {

    @Autowired
    private JpaAppointmentRepository jpaAppointmentRepository;

    @Autowired
    private SpringDataAppointmentRepository springDataAppointmentRepository;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    @Test
    public void testSaveAndFindById() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajaro");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("+123456789");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment savedAppointment = jpaAppointmentRepository.save(appointment);
        Optional<Appointment> foundAppointment = jpaAppointmentRepository.findById(savedAppointment.getId());

        assertTrue(foundAppointment.isPresent());
        assertEquals(savedAppointment.getId(), foundAppointment.get().getId());
    }

    @Test
    public void testFindAll() {
        Appointment appointment1 = new Appointment();
        appointment1.setFirstName("Juan");
        appointment1.setLastName("Fajardo");
        appointment1.setEmail("juan@example.com");
        appointment1.setTlph("+123456789");
        appointment1.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment appointment2 = new Appointment();
        appointment2.setFirstName("Juan");
        appointment2.setLastName("Fajardo");
        appointment2.setEmail("juah@example.com");
        appointment2.setTlph("+987654321");
        appointment2.setDateReservation(LocalDateTime.now().plusDays(2));

        springDataAppointmentRepository.save(appointment1);
        springDataAppointmentRepository.save(appointment2);

        Iterable<Appointment> appointments = jpaAppointmentRepository.findAll();
        assertTrue(appointments.iterator().hasNext());
    }

    @Test
    public void testCountByDateReservationBetween() {
        // Crea y guarda citas
        Appointment appointment1 = new Appointment();
        appointment1.setFirstName("Juan");
        appointment1.setLastName("Fajardo");
        appointment1.setEmail("juan@example.com");
        appointment1.setTlph("+123456789");
        appointment1.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment appointment2 = new Appointment();
        appointment2.setFirstName("Juan");
        appointment2.setLastName("Fajardo");
        appointment2.setEmail("juan@example.com");
        appointment2.setTlph("+987654321");
        appointment2.setDateReservation(LocalDateTime.now().plusDays(1).plusHours(1));

        springDataAppointmentRepository.save(appointment1);
        springDataAppointmentRepository.save(appointment2);

        // Cuenta el número de citas en el intervalo de tiempo especificado
        LocalDateTime startSlot = LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0);
        LocalDateTime endSlot = startSlot.plusHours(2);
        long count = jpaAppointmentRepository.countByDateReservationBetween(startSlot, endSlot);

        assertEquals(2, count);
    }
}
