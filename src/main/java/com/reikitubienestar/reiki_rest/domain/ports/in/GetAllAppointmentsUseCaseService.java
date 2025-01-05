package com.reikitubienestar.reiki_rest.domain.ports.in;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

import java.util.List;

public interface GetAllAppointmentsUseCaseService {
    List<AppointmentDTO> getAllAppointments();
}
