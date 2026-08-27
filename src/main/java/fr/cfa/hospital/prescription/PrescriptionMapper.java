package fr.cfa.hospital.prescription;

import fr.cfa.hospital.prescription.dtos.PrescriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * The interface Prescription mapper.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrescriptionMapper {
    PrescriptionDto toDto(Prescription entity);
}
