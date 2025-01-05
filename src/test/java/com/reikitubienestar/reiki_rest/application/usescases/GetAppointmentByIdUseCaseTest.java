package com.reikitubienestar.reiki_rest.application.usescases;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.exception.AppointmentNotFoundException;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
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
import static org.mockito.Mockito.when;


class GetAppointmentByIdUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private GetAppointmentByIdUseCase getAppointmentByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAppointmentById_success() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointmentId);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentMapper.entityToDTO(appointment)).thenReturn(appointmentDTO);

        Optional<AppointmentDTO> result = getAppointmentByIdUseCase.getAppointmentById(appointmentId);

        assertNotNull(result);
        assertEquals(appointmentId, result.get().getId());
        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void getAppointmentById_appointmentNotFound() {
        Long appointmentId = 1L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> getAppointmentByIdUseCase.getAppointmentById(appointmentId));
    }
}
