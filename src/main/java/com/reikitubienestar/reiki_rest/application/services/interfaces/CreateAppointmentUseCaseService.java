package com.reikitubienestar.reiki_rest.application.services.interfaces;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

public interface CreateAppointmentUseCaseService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

}