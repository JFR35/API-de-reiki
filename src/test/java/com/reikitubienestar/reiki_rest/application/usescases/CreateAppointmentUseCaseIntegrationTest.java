package com.reikitubienestar.reiki_rest.application.usescases;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsBelowMaxAppointmentsService;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsValidAppointmentTimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CreateAppointmentUseCaseIntegrationTest {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @MockBean
    private IsValidAppointmentTimeService appointmentValidationService;

    @MockBean
    private IsBelowMaxAppointmentsService isBelowMaxCitasService;
    @MockBean
    private AppointmentRepository appointmentRepository;

    @MockBean
    private AppointmentMapper appointmentMapper;

    @Test
    public void testCreateAppointment() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setFirstName("John");
        appointmentDTO.setLastName("Doe");
        appointmentDTO.setEmail("john.doe@example.com");
        appointmentDTO.setTlph("+123456789");
        appointmentDTO.setDateReservation(LocalDateTime.now().plusDays(1));

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setFirstName("John");
        appointment.setLastName("Doe");
        appointment.setEmail("john.doe@example.com");
        appointment.setTlph("+123456789");
        appointment.setDateReservation(LocalDateTime.now().plusDays(1));

        when(appointmentValidationService.isValidAppointmentTime(any())).thenReturn(true);
        when(isBelowMaxCitasService.isBelowMaxAppoinemntService(any())).thenReturn(true);
        when(appointmentMapper.dtoToEntity(any())).thenReturn(appointment);
        when(appointmentRepository.save(any())).thenReturn(appointment);
        when(appointmentMapper.entityToDTO(any())).thenReturn(appointmentDTO);

        AppointmentDTO result = createAppointmentUseCase.createAppointment(appointmentDTO);

        assertNotNull(result);
        assertEquals(appointmentDTO.getFirstName(), result.getFirstName());
    }
}
