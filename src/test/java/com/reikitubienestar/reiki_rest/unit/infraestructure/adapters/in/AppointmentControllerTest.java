package com.reikitubienestar.reiki_rest.unit.infraestructure.adapters.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.exception.AppointmentNotFoundException;
import com.reikitubienestar.reiki_rest.application.usescases.CreateAppointmentUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.DeleteAppointmentUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.GetAllAppointmentsUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.GetAppointmentByIdUseCase;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.out.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAppointmentUseCase createAppointmentUseCase;

    @MockBean
    private DeleteAppointmentUseCase deleteAppointmentUseCase;

    @MockBean
    private GetAllAppointmentsUseCase getAllAppointmentsUseCase;

    @MockBean
    private GetAppointmentByIdUseCase getAppointmentByIdUseCase;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAppointment_withEmail_success() throws Exception {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setFirstName("Juan");
        appointmentDTO.setLastName("Pérez");
        appointmentDTO.setEmail("juan.perez@example.com");
        appointmentDTO.setTlph("+34123456789");
        appointmentDTO.setDateReservation(LocalDateTime.now().plusDays(1));

        when(createAppointmentUseCase.createAppointment(any(AppointmentDTO.class))).thenReturn(appointmentDTO);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        mockMvc.perform(post("/api/v1/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Juan"));

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void getAppointmentById_success() throws Exception {
        Long appointmentId = 1L;
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointmentId);
        appointmentDTO.setFirstName("John");

        when(getAppointmentByIdUseCase.getAppointmentById(appointmentId)).thenReturn(Optional.of(appointmentDTO));

        mockMvc.perform(get("/api/v1/citas/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getAppointmentById_notFound() throws Exception {
        Long appointmentId = 1L;

        when(getAppointmentByIdUseCase.getAppointmentById(appointmentId)).thenThrow(new AppointmentNotFoundException("Cita no encontrada con el ID: " + appointmentId));

        mockMvc.perform(get("/api/v1/citas/{id}", appointmentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAppointments_success() throws Exception {
        List<AppointmentDTO> appointments = new ArrayList<>();
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setFirstName("John");
        appointments.add(appointmentDTO);

        when(getAllAppointmentsUseCase.getAllAppointments()).thenReturn(appointments);

        mockMvc.perform(get("/api/v1/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void deleteAppointment_success() throws Exception {
        Long appointmentId = 1L;
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointmentId);
        appointmentDTO.setFirstName("John");

        when(getAppointmentByIdUseCase.getAppointmentById(appointmentId)).thenReturn(Optional.of(appointmentDTO));

        mockMvc.perform(delete("/api/v1/citas/{id}", appointmentId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAppointment_notFound() throws Exception {
        Long appointmentId = 1L;

        when(getAppointmentByIdUseCase.getAppointmentById(appointmentId)).thenThrow(new AppointmentNotFoundException("Cita no encontrada con el ID: " + appointmentId));

        mockMvc.perform(delete("/api/v1/citas/{id}", appointmentId))
                .andExpect(status().isNotFound());
    }
}
