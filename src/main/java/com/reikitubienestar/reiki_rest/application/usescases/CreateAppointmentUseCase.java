package com.reikitubienestar.reiki_rest.application.usescases;


import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.exception.InvalidAppointmentTimeException;
import com.reikitubienestar.reiki_rest.application.exception.MaxAppointmentException;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.domain.ports.in.CreateAppointmentUseCaseService;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsBelowMaxAppointmentsService;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsValidAppointmentTimeService;
import com.reikitubienestar.reiki_rest.infraestructure.adapters.out.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateAppointmentUseCase implements CreateAppointmentUseCaseService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private IsValidAppointmentTimeService isValidAppointmentTimeService;
    @Autowired
    private IsBelowMaxAppointmentsService isBelowMaxAppointmentsService;
    @Autowired
    private EmailService emailService;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        LocalDateTime dateReservation = appointmentDTO.getDateReservation();
        if (dateReservation.isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentTimeException("Reservation date can't be in the past");
        }
        if (!isValidAppointmentTimeService.isValidAppointmentTime(dateReservation)) {
            throw new InvalidAppointmentTimeException("Invalid time for appointment");
        }
        if (!isBelowMaxAppointmentsService.isBelowMaxAppoinemntService(dateReservation)) {
            throw new MaxAppointmentException("Over Booking");
        }
        Appointment appointment = appointmentMapper.dtoToEntity(appointmentDTO);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        // Enviar correo al usuario
        emailService.sendEmail(appointmentDTO.getEmail(), "Confirmación de Cita", "Tu cita ha sido reservada para: " + dateReservation);
        // Enviar correo al empleado
        emailService.sendEmail("empleado@example.com", "Nueva Reserva de Cita", "Nueva cita reservada para " + dateReservation + " por " + appointmentDTO.getFirstName() + " " + appointmentDTO.getLastName() + " El día: " + appointmentDTO.getDateReservation() + " Con telefono: " + appointmentDTO.getTlph());
        return appointmentMapper.entityToDTO(savedAppointment);
    }
}