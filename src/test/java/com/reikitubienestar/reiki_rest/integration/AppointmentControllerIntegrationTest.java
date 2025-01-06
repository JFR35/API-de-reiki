package com.reikitubienestar.reiki_rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.application.usescases.CreateAppointmentUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.DeleteAppointmentUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.GetAllAppointmentsUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.GetAppointmentByIdUseCase;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.infraestructure.repositories.SpringDataAppointmentRepository;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.out.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataAppointmentRepository appointmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @MockBean
    private DeleteAppointmentUseCase deleteAppointmentUseCase;

    @MockBean
    private GetAllAppointmentsUseCase getAllAppointmentsUseCase;

    @MockBean
    private GetAppointmentByIdUseCase getAppointmentByIdUseCase;

    @MockBean
    private EmailService emailService;

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    @Test
    void createAppointmentTest() throws Exception {
        // Crear un AppointmentDTO de prueba
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setFirstName("Juan");
        appointmentDTO.setLastName("Pérez");
        appointmentDTO.setEmail("juan.perez@example.com");
        appointmentDTO.setTlph("+34123456789");
        appointmentDTO.setDateReservation(LocalDateTime.now().plusDays(1));

        // Hacer una solicitud POST para crear la cita
        mockMvc.perform(post("/api/v1/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentDTO)))
                .andExpect(status().isCreated())
                .andDo(print());

        // Verificar que la cita ha sido guardada
        Optional<Appointment> savedAppointment = appointmentRepository.findAll().stream().findFirst();
        assertThat(savedAppointment).isPresent();
        assertThat(savedAppointment.get().getFirstName()).isEqualTo("Juan");
    }

    @Test
    void deleteAppointmentByIdTest() throws Exception {
        // Crear y guardar una cita de prueba
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setFirstName("Juan");
        appointmentDTO.setLastName("Pérez");
        appointmentDTO.setEmail("juan.perez@example.com");
        appointmentDTO.setTlph("+34123456789");
        appointmentDTO.setDateReservation(LocalDateTime.now().plusDays(1));
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        // Verificar que la cita ha sido guardada
        Optional<Appointment> existBeforeDelete = appointmentRepository.findById(appointment.getId());
        assertThat(existBeforeDelete).isPresent();
        System.out.println("Cita guardada con ID: " + appointment.getId());
        // Eliminar la cita directamente desde el repositorio
        appointmentRepository.deleteById(appointment.getId());
        // Verificar que la cita ha sido eliminada
        Optional<Appointment> existAfterDelete = appointmentRepository.findById(appointment.getId());
        assertThat(existAfterDelete).isNotPresent();
        System.out.println("Cita eliminada con ID: " + appointment.getId());
    }
}

