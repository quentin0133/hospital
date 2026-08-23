package fr.cfa.hospital.patient.dtos;

import fr.cfa.hospital.core.generic.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDto extends BaseDto {
    private long socialSecurityNumber;

    private String name;
}
