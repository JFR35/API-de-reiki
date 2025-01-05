package com.reikitubienestar.reiki_rest.domain.ports.in;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

public interface CreateAppointmentUseCaseService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

}