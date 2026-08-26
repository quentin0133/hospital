package fr.cfa.hospital.doctor;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.doctor.dtos.DoctorPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorMapper extends GenericMapper<Doctor, DoctorDto, DoctorPostDto> {
    @Override
    @Mapping(target = "consultations", ignore = true)
    Doctor toEntity(DoctorPostDto dto);
}
