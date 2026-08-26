package fr.cfa.hospital.doctor;

import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.doctor.dtos.DoctorPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {
    Page<DoctorDto> findAll(Pageable pageable);

    List<DoctorDto> findAll();

    Page<DoctorDto> findByName(String search, Pageable pageable);

    DoctorDto save(DoctorPostDto dto);

    DoctorDto update(DoctorPostDto dto);

    void deleteById(long id);

    DoctorDto findById(long id);

    Page<DoctorDto> findByNameContains(String name, Pageable pageable);
}
