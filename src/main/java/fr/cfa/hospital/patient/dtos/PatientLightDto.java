package fr.cfa.hospital.patient.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * The type Patient light dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientLightDto {
    private long id;
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PatientLightDto dto = (PatientLightDto) object;
        return id == dto.id && Objects.equals(name, dto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PatientDto{" +
            "id=" + getId() +
            ", name='" + getName() + '\'' +
            '}';
    }
}
