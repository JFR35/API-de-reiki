package com.reikitubienestar.reiki_rest.application.usescases;

import com.reikitubienestar.reiki_rest.application.exception.AppointmenNotFoundException;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.domain.ports.in.GetAppointmentByIdUseCaseService;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetAppointmentByIdUseCase implements GetAppointmentByIdUseCaseService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        if(appointmentRepository.findById(id).isEmpty()){
            throw new AppointmenNotFoundException("Appointment not found by id:  " + id);
        }
        return appointmentRepository.findById(id);
    }
}
