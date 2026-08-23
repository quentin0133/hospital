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
public class ConsultationPostDto extends BaseDto {
    private long number;

    private LocalDate date;

    private List<Long> idsMedication;

    private long idDoctor;

    private long idPatient;
}
