package com.reikitubienestar.reiki_rest.unit.domain.services.impl;

import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.impl.IsValidAppointmentTimeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IsValidAppointmentTimeServiceImplTest {

    @InjectMocks
    private IsValidAppointmentTimeServiceImpl isValidAppointmentTimeService;
    @Mock
    private AppointmentRepository appointmentRepository;

    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        // Fecha y hora fija para el test: lunes 1 de mayo de 2023 a las 9:00
        ZonedDateTime fixedDateTime = ZonedDateTime.of(2023, 5, 1, 9, 0, 0, 0, ZoneId.systemDefault());
        fixedClock = Clock.fixed(fixedDateTime.toInstant(), ZoneId.systemDefault());
        isValidAppointmentTimeService.setClock(fixedClock);
    }

    // Dentro de horario de mañana
    @Test
    void isValidAppointmentTime_withinWorkingHours_onWeekday() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 2, 11, 0);
        assertTrue(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }

    // Fuera de horario permitido
    @Test
    void isValidAppointmentTime_outsideWorkingHours_onWeekday() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 2, 21, 0);
        assertFalse(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }

    @Test
    void isValidAppointmentTime_withinWorkingHours_onSaturday() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 6, 11, 0);
        assertTrue(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }

    // Sábado 6 de mayo de 2023 a las 14:00, fuera del horario permitido para sábados
    @Test
    void isValidAppointmentTime_outsideWorkingHours_onSaturday() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 6, 14, 0);
        assertFalse(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }

    // Domingo 7 de mayo de 2023 a las 11:00, ningún horario permitido en domingo
    @Test
    void isValidAppointmentTime_onSunday() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 7, 11, 0);
        assertFalse(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }

    // Domingo 30 de abril de 2023 a las 11:00, una fecha en el pasado
    @Test
    void isValidAppointmentTime_beforeNow() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 4, 30, 11, 0);
        assertFalse(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }

    // Martes 9 de mayo de 2023 a las 11:00, más de una semana en el futuro
    @Test
    void isValidAppointmentTime_afterOneWeek() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 9, 11, 0);
        assertFalse(isValidAppointmentTimeService.isValidAppointmentTime(dateTime));
    }
}