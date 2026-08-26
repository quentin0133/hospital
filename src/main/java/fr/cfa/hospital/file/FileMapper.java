package fr.cfa.hospital.file;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileMapper extends GenericMapper<File, FileDto, FilePostDto> {
    @Override
    @Mapping(source = "file.originalFilename", target = "fileName")
    @Mapping(target = "storedFileName", ignore = true)
    File toEntity(FilePostDto dto);
}
