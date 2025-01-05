package com.reikitubienestar.reiki_rest.domain.ports.in;

import com.reikitubienestar.reiki_rest.domain.models.Appointment;

import java.util.Optional;

public interface GetAppointmentByIdUseCaseService {
    Optional<Appointment> getAppointmentById(Long id);
}

