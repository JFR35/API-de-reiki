package com.reikitubienestar.reiki_rest.application.usescases;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.domain.ports.in.GetAllAppointmentsUseCaseService;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAppointmentsUseCase implements GetAllAppointmentsUseCaseService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> appointmentMapper.entityToDTO(appointment))
                .collect(Collectors.toList());
    }
}
