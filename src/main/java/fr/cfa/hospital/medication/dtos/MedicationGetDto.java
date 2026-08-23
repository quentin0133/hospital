package fr.cfa.hospital.medication.dtos;

import fr.cfa.hospital.core.generic.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicationGetDto extends BaseDto {
    private long code;

    private String label;

    @Override
    public String toString() {
        return "MedicationGetDto{" +
            "code=" + code +
            ", label='" + label + '\'' +
            '}';
    }
}
