package fr.cfa.hospital.patient;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.patient.dtos.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper extends GenericMapper<Patient, PatientDto, PatientDto> {
}
