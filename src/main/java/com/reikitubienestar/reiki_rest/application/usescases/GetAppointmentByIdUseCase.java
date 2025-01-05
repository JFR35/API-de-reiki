package com.reikitubienestar.reiki_rest.application.usescases;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.exception.AppointmentNotFoundException;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.domain.ports.in.GetAppointmentByIdUseCaseService;
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


    public Optional<AppointmentDTO> getAppointmentById(Long appointmentId) {
        return Optional.ofNullable(appointmentRepository.findById(appointmentId)
                .map(appointmentMapper::entityToDTO)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId)));
    }
}

