package fr.cfa.hospital.doctor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * The type Doctor light dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorLightDto {
    private long id;

    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DoctorLightDto doctorLightDto = (DoctorLightDto) object;
        return id == doctorLightDto.id && Objects.equals(name, doctorLightDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DoctorDto{" +
            "id=" + getId() +
            ", name='" + getName() + '\'' +
            '}';
    }
}
