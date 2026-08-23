package fr.cfa.hospital.medication.dtos;

import fr.cfa.hospital.core.generic.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicationPostDto extends BaseDto {
    private long code;

    private String label;

    private List<Long> idsConsultations;

    @Override
    public String toString() {
        return "MedicationPostDto{" +
            "code=" + code +
            ", label='" + label + '\'' +
            ", idsConsultations=" + idsConsultations +
            '}';
    }
}
