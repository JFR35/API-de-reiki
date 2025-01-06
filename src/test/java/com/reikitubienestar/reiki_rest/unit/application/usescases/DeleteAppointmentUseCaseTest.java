package com.reikitubienestar.reiki_rest.unit.application.usescases;

import com.reikitubienestar.reiki_rest.application.usescases.DeleteAppointmentUseCase;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private DeleteAppointmentUseCase deleteAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteAppointment_success() {
        Long appointmentId = 1L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(new Appointment()));

        deleteAppointmentUseCase.deleteAppointmentById(appointmentId);

        verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }


}
