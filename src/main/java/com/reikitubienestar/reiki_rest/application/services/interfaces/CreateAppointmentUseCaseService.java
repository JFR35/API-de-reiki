package com.reikitubienestar.reiki_rest.application.usescases.interfaces;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

public interface CreateAppointmentUseCaseService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

}