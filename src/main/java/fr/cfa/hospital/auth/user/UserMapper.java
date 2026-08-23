package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends GenericMapper<User, UserDto, UserDto> {
}
