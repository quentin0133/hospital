package fr.cfa.hospital.doctor.dtos;

/**
 * The type Doctor light dto.
 */
public class DoctorDto {
    private long id;

    private int version;

    private String name;

    public DoctorDto() {
    }

    public DoctorDto(long id, int version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DoctorDto doctorDto = (DoctorDto) object;
        return id == doctorDto.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "DoctorDto{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}
