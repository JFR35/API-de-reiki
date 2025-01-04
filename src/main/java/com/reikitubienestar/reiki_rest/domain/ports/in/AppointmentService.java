package com.reikitubienestar.reiki_rest.domain.ports.in;

import com.reikitubienestar.reiki_rest.domain.models.Appointment;

import java.util.Optional;

public interface AppointmentService {
    Optional<Appointment> getAppointmentById(Long id);

    Appointment createAppointment(Appointment appointment);

    void deleteAppointment(Long id);

    boolean isBelowMaxAppointments(Appointment appointment);

    boolean isValidAppointmentTime(Appointment appointment);
}
