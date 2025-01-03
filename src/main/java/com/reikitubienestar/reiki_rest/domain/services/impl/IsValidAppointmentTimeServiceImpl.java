package com.reikitubienestar.reiki_rest.domain.services.impl;

import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsValidAppointmentTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class IsValidAppointmentTimeServiceImpl implements IsValidAppointmentTimeService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final LocalTime MORNING_START = LocalTime.of(10, 0);
    private final LocalTime MORNING_END = LocalTime.of(13, 30);
    private final LocalTime EVENING_START = LocalTime.of(17, 0);
    private final LocalTime EVENING_END = LocalTime.of(20, 0);
    private static final LocalTime SATURDAY_START = LocalTime.of(10, 0);
    private static final LocalTime SATURDAY_END = LocalTime.of(13, 30);

    private Clock clock = Clock.systemDefaultZone(); // Usar Clock para controlar el tiempo

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public boolean isValidAppointmentTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime maxDate = now.plusDays(7);

        if (dateTime.isBefore(now) || dateTime.isAfter(maxDate)) {
            return false;
        }

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();

        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return false;
        }

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return !time.isBefore(SATURDAY_START) && !time.isAfter(SATURDAY_END);
        }

        boolean isMorning = !time.isBefore(MORNING_START) && !time.isAfter(MORNING_END);
        boolean isEvening = !time.isBefore(EVENING_START) && !time.isAfter(EVENING_END);

        return isMorning || isEvening;
    }
}
