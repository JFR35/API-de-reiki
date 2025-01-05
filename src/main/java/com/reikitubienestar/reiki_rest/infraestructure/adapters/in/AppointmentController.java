package com.reikitubienestar.reiki_rest.infraestructure.adapters.in;


import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.exception.AppointmentNotFoundException;
import com.reikitubienestar.reiki_rest.application.usescases.CreateAppointmentUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.DeleteAppointmentUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.GetAllAppointmentsUseCase;
import com.reikitubienestar.reiki_rest.application.usescases.GetAppointmentByIdUseCase;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.out.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/citas")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final DeleteAppointmentUseCase deleteAppointmentUseCase;
    private final GetAllAppointmentsUseCase getAllAppointmentsUseCase;
    private final GetAppointmentByIdUseCase getAppointmentByIdUseCase;
    private final EmailService emailService;

    public AppointmentController(
            CreateAppointmentUseCase createAppointmentUseCase,
            DeleteAppointmentUseCase deleteAppointmentUseCase,
            GetAllAppointmentsUseCase getAllAppointmentsUseCase,
            GetAppointmentByIdUseCase getAppointmentByIdUseCase,
            EmailService emailService) {
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.deleteAppointmentUseCase = deleteAppointmentUseCase;
        this.getAllAppointmentsUseCase = getAllAppointmentsUseCase;
        this.getAppointmentByIdUseCase = getAppointmentByIdUseCase;
        this.emailService = emailService;
    }


    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO createdAppointment = createAppointmentUseCase.createAppointment(appointmentDTO);

        // Ubicación del nuevo recurso, asumiendo que el ID es un valor único
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAppointment.getId())
                .toUri();

        // Enviar correo electrónico de confirmación
        emailService.sendEmail(
                appointmentDTO.getEmail(),
                "Confirmación de cita",
                "Hola " + appointmentDTO.getFirstName() + ", tu cita ha sido creada para " + appointmentDTO.getDateReservation().toString()
        );

        return ResponseEntity.created(location).body(createdAppointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AppointmentDTO>> findById(@PathVariable Long id) {
        Optional<AppointmentDTO> appointmentDTO = getAppointmentByIdUseCase.getAppointmentById(id); // Llamada al caso de uso

        if (appointmentDTO.isPresent()) {
            return ResponseEntity.ok(appointmentDTO);  // Devuelve la cita con código 200 OK
        } else {
            throw new AppointmentNotFoundException("Cita no encontrada con el ID: " + id);  // Lanza una excepción si no se encuentra
        }
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = getAllAppointmentsUseCase.getAllAppointments(); // Llamada al caso de uso

        if (!appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);  // Devuelve las citas con código 200 OK
        } else {
            return ResponseEntity.noContent().build();  // Devuelve código 204 No Content si no hay citas
        }
    }

    @DeleteMapping("/{id}")
    public Optional<AppointmentDTO> deleteAppointment(@PathVariable Long id) {
        // Obtén la cita para verificar que existe
        Optional<AppointmentDTO> appointmentDTO = getAppointmentByIdUseCase.getAppointmentById(id);

        if (appointmentDTO.isPresent()) {
            // Llama al caso de uso para eliminar la cita
            deleteAppointmentUseCase.deleteAppointmentById(id);
            return appointmentDTO; // Devuelve la cita eliminada como confirmación
        } else {
            throw new AppointmentNotFoundException("Cita no encontrada con el ID: " + id); // Lanza una excepción si no se encuentra
        }
    }

}