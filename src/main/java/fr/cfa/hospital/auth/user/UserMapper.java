package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.core.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends GenericMapper<User, UserDto, UserDto> {
    @Override
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto dto);
}
