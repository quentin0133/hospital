package fr.cfa.hospital.medication;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicationMapper extends GenericMapper<Medication, MedicationDto, MedicationPostDto> {
}
