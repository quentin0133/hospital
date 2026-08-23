package fr.cfa.hospital.consultation.dtos;

import fr.cfa.hospital.core.generic.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultationGetDto extends BaseDto {
    private long number;

    private LocalDate date;

    private List<MedicationWithoutConsultationDto> medications;
}
