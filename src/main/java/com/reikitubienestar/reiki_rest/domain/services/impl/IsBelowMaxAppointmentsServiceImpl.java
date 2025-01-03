package com.reikitubienestar.reiki_rest.domain.services.impl;

import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import com.reikitubienestar.reiki_rest.domain.services.interfaces.IsBelowMaxAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IsBelowMaxAppointmentsServiceImpl implements IsBelowMaxAppointmentsService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public boolean isBelowMaxAppoinemntService(LocalDateTime dateTime) {
        LocalDateTime startSlot = dateTime.withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endSlot = startSlot.plusMinutes(90);

        long count = appointmentRepository.countByDateReservationBetween(startSlot, endSlot);
        return count < 1;
    }
}
