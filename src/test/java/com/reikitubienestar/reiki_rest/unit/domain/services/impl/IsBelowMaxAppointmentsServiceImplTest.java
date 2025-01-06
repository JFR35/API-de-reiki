package com.reikitubienestar.reiki_rest.unit.domain.services.impl;

import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.impl.IsBelowMaxAppointmentsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsBelowMaxAppointmentsServiceImplTest {
    @InjectMocks
    private IsBelowMaxAppointmentsServiceImpl isBelowMaxCitasService;
    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    public void isBelowMaxCitas_test_noOverbooking() {
        when(appointmentRepository.countByDateReservationBetween(any(), any())).thenReturn(0L);
        boolean isBelowMax = isBelowMaxCitasService.isBelowMaxAppoinemntService(LocalDateTime.now().plusHours(1));
        assertTrue(isBelowMax);
    }

    @Test
    public void isBelowMaxCitas_test_overbooking() {
        when(appointmentRepository.countByDateReservationBetween(any(), any())).thenReturn(1L);
        boolean isBelowMax = isBelowMaxCitasService.isBelowMaxAppoinemntService(LocalDateTime.now().plusHours(1));
        assertFalse(isBelowMax);
    }

}