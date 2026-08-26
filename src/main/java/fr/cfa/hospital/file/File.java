package fr.cfa.hospital.file;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "stored_file_name", unique = true)
    private String storedFileName;

    public File() {
    }

    public File(String fileName, String storedFileName) {
        this.fileName = fileName;
        this.storedFileName = storedFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        File that = (File) object;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                '}';
    }
}
