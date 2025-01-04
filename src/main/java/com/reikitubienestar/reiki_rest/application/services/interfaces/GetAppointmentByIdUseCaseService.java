package com.reikitubienestar.reiki_rest.application.services.interfaces;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;

import java.util.Optional;

public interface GetAppointmentByIdUseCaseService {
    AppointmentDTO getAppointmentById(Long id);
}

