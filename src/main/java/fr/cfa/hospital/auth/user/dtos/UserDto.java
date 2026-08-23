package fr.cfa.hospital.auth.user.dtos;

import java.io.Serializable;
import java.util.List;

public record UserDto(long id, String username, String email, List<String> roles)
        implements Serializable {
}
