package com.reikitubienestar.reiki_rest.unit.application.usescases;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.application.usescases.CreateAppointmentUseCase;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsBelowMaxAppointmentsService;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsValidAppointmentTimeService;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.out.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CreateAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private IsValidAppointmentTimeService isValidAppointmentTimeService;

    @Mock
    private IsBelowMaxAppointmentsService isBelowMaxAppointmentsService;

    @InjectMocks
    private CreateAppointmentUseCase createAppointmentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAppointmentSuccess_test() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setFirstName("Juan");
        appointmentDTO.setLastName("Fajardo");
        appointmentDTO.setEmail("juan@hotmail.com");
        appointmentDTO.setTlph("+34123456");
        appointmentDTO.setDateReservation(LocalDateTime.of(2025, 1, 7, 11, 0));

        Appointment appointment = new Appointment();
        appointment.setFirstName(appointmentDTO.getFirstName());
        appointment.setLastName(appointmentDTO.getLastName());
        appointment.setEmail(appointmentDTO.getEmail());
        appointment.setTlph(appointmentDTO.getTlph());
        appointment.setDateReservation(appointmentDTO.getDateReservation());

        when(isValidAppointmentTimeService.isValidAppointmentTime(any(LocalDateTime.class))).thenReturn(true);
        when(isBelowMaxAppointmentsService.isBelowMaxAppoinemntService(any(LocalDateTime.class))).thenReturn(true);
        when(appointmentMapper.dtoToEntity(any(AppointmentDTO.class))).thenReturn(appointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentMapper.entityToDTO(any(Appointment.class))).thenReturn(appointmentDTO);

        AppointmentDTO result = createAppointmentUseCase.createAppointment(appointmentDTO);

        assertEquals("Juan", result.getFirstName());
        verify(emailService, times(1)).sendEmail(eq("juan@hotmail.com"), anyString(), anyString());
        verify(emailService, times(1)).sendEmail(eq("empleado@example.com"), anyString(), anyString());
    }
}
