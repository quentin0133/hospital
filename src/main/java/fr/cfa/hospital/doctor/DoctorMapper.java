package fr.cfa.hospital.doctor;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorMapper extends GenericMapper<Doctor, DoctorDto, DoctorDto> {
}
