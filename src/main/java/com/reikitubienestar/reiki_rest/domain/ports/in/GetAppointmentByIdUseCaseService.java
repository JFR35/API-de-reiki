package com.reikitubienestar.reiki_rest.domain.ports.in;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

import java.util.Optional;

public interface GetAppointmentByIdUseCaseService {
    Optional<AppointmentDTO> getAppointmentById(Long id);
}

