package com.reikitubienestar.reiki_rest.domain.services.interfaces;

import java.time.LocalDateTime;

public interface IsValidAppointmentTimeService {
    boolean isValidAppointmentTime(LocalDateTime dateTime);
}
