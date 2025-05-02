package fr.cfa.hospital.core.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto implements Serializable {
    private long id;

    private int version;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BaseDto baseDto)) return false;
        return id == baseDto.id && version == baseDto.version;
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(id) + version;
    }
}
