package fr.cfa.hospital.auth.dtos;

import fr.cfa.hospital.auth.user.dtos.UserDto;

import java.util.Objects;

public class LoginDto {
    private UserDto user;
    private String token;

    public LoginDto() {
    }

    public LoginDto(UserDto user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginDto that = (LoginDto) o;
        return Objects.equals(user, that.user) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}