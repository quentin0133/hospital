package fr.cfa.hospital.auth.dtos;

import java.io.Serializable;
import java.util.List;

public record LoginGetDto(UserDto user, String token) implements Serializable {
    public record UserDto(long id, String username, List<String> roles)
            implements Serializable {
    }
}
