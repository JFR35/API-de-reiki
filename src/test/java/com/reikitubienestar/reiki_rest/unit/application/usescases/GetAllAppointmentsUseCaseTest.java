package com.reikitubienestar.reiki_rest.unit.application.usescases;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.application.usescases.GetAllAppointmentsUseCase;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GetAllAppointmentsUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private GetAllAppointmentsUseCase getAllAppointmentsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAppointments_success() {
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment());
        appointmentList.add(new Appointment());

        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        appointmentDTOList.add(new AppointmentDTO());
        appointmentDTOList.add(new AppointmentDTO());

        when(appointmentRepository.findAll()).thenReturn(appointmentList);
        when(appointmentMapper.entityToDTOList(appointmentList)).thenReturn(appointmentDTOList);

        List<AppointmentDTO> result = getAllAppointmentsUseCase.getAllAppointments();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }
}
