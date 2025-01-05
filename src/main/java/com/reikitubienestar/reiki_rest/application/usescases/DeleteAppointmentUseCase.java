package com.reikitubienestar.reiki_rest.application.usescases;

import com.reikitubienestar.reiki_rest.application.exception.AppointmenNotFoundException;
import com.reikitubienestar.reiki_rest.application.mapper.AppointmentMapper;
import com.reikitubienestar.reiki_rest.domain.ports.in.DeleteAppointmentUseCaseImp;
import com.reikitubienestar.reiki_rest.domain.ports.out.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAppointmentUseCase implements DeleteAppointmentUseCaseImp {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;

    public void deleteAppointmentById(Long id) {
        if (appointmentRepository.findById(id).isEmpty()) {
            throw new AppointmenNotFoundException("Appointment not found by id:  " + id);
        }
        appointmentRepository.deleteById(id);
    }

}
