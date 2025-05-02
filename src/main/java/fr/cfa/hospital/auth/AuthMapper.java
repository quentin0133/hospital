package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginGetDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    @Mapping(target = "token", expression = "java(fr.cfa.hospital.core.tools.JwtUtils.generateToken(security))")
    LoginGetDto toLoginResponse(UserSecurity security);
}
