package com.reikitubienestar.reiki_rest.application.usescases.interfaces;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

import java.util.List;

public interface GetAllAppointmentsUseCaseService {
    List<AppointmentDTO> getAllAppointments();
}
