package com.reikitubienestar.reiki_rest.domain.models;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {


    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validAppointment_test() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajardo");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("+34655768222");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidFirstName_test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("1234");
        appointment.setLastName("Fajardo");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("+34655768222");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertFalse(violations.isEmpty());
        assertEquals("First name must contain only letters and valid characters", violations.iterator().next().getMessage());
    }


    @Test
    public void invalidLastName_test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("1234");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("+34655768222");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertFalse(violations.isEmpty());
        assertEquals("Last name must contain only letters and valid characters", violations.iterator().next().getMessage());
    }

    @Test
    public void invalidEmail_test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajardo");
        appointment.setEmail("invalid-email");
        appointment.setTlph("+34655768222");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertFalse(violations.isEmpty());
        assertEquals("Invalid email format", violations.iterator().next().getMessage());
    }

    @Test
    public void invalidPhone_test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajardo");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("invalid-phone");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertFalse(violations.isEmpty());
        assertEquals("Phone number must be valid and between 7 and 15 digits", violations.iterator().next().getMessage());
    }

    @Test
    public void nullDateReservation_test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajardo");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("+34655768222");
        appointment.setDateReservation(null);

        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        assertFalse(violations.isEmpty());
        assertEquals("Reservation date is mandatory", violations.iterator().next().getMessage());
    }

    // Prueba para verificar los getters y setters
    @Test
    public void gettersAndSetters_test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajardo");
        appointment.setEmail("juan@example.com");
        appointment.setTlph("+34655768222");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        assertEquals("Juan", appointment.getFirstName());
        assertEquals("Fajardo", appointment.getLastName());
        assertEquals("juan@example.com", appointment.getEmail());
        assertEquals("+34655768222", appointment.getTlph());
        assertNotNull(appointment.getDateReservation());
    }

}