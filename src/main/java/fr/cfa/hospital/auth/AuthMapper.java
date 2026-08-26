package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "token", source = "token")
    LoginDto toLoginResponse(UserDto userDto, String token);
}
