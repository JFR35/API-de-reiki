package com.reikitubienestar.reiki_rest.application.mapper;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class AppointmentMapperTest {
    private final AppointmentMapper mapper = Mappers.getMapper(AppointmentMapper.class);
    private AppointmentDTO appointmentDTO;

    /*
    Test para comprobar mapeo Entidad a DTO
     */
    @Test
    public void entityToDTO_Test() {
        Appointment appointment = new Appointment();
        appointment.setFirstName("Juan");
        appointment.setLastName("Fajardo");
        appointment.setTlph("655768222");
        appointment.setEmail("juan@example.com");

        AppointmentDTO dto = mapper.entityToDTO(appointment);
        assertEquals(appointment.getFirstName(), dto.getFirstName());
        assertEquals(appointment.getLastName(), dto.getLastName());
        assertEquals(appointment.getEmail(), dto.getEmail());
        assertEquals(appointment.getTlph(), dto.getTlph());

    }

    /*
    Test para comprobar el mapeo de DTO a Entidad
     */
    @Test
    public void dtoToEntity_test() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setFirstName("Juan");
        appointmentDTO.setLastName("Fajardo");
        appointmentDTO.setTlph("655768222");
        appointmentDTO.setEmail("juan@example.com");
        appointmentDTO.setDateReservation(LocalDateTime.now());

        Appointment appointment = mapper.dtoToEntity(appointmentDTO);
        assertEquals(appointmentDTO.getFirstName(), appointment.getFirstName());
        assertEquals(appointmentDTO.getLastName(), appointment.getLastName());
        assertEquals(appointmentDTO.getEmail(), appointment.getEmail());
        assertEquals(appointmentDTO.getTlph(), appointment.getTlph());

    }

}