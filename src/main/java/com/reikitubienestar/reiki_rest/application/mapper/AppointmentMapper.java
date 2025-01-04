package com.reikitubienestar.reiki_rest.application.mapper;

import com.reikitubienestar.reiki_rest.application.dto.AppointmentDTO;
import com.reikitubienestar.reiki_rest.domain.models.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO entityToDTO(Appointment appointment);
    Appointment dtoToEntity(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> entityToDTOList(List<Appointment> appointments);
    List<Appointment> dtoToEntityList(List<AppointmentDTO> appointmentDTOs);
}