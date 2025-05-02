package fr.cfa.hospital.auth;

import fr.quentin.portfolio.portfolioback.auth.dtos.LoginResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    @Mapping(target = "token", expression = "java(fr.quentin.portfolio.portfolioback.core.tools.JwtUtils.generateToken(security))")
    LoginResponseDto toLoginResponse(UserSecurity security);
}
