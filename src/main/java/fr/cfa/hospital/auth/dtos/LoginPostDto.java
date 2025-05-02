package fr.cfa.hospital.auth.dtos;

import java.io.Serializable;

public record LoginPostDto(String username, String password) implements Serializable {
}
