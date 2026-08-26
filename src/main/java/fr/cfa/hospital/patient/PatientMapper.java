package fr.cfa.hospital.patient;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.patient.dtos.PatientDto;
import fr.cfa.hospital.patient.dtos.PatientPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper extends GenericMapper<Patient, PatientDto, PatientPostDto> {
    @Override
    @Mapping(target = "consultations", ignore = true)
    Patient toEntity(PatientPostDto dto);
}
