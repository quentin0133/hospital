package fr.cfa.hospital.auth.dtos;

import java.util.Objects;

public class LoginPostDto {
    private String username;
    private String password;

    public LoginPostDto() {
    }

    public LoginPostDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginPostDto loginPostDto = (LoginPostDto) o;
        return Objects.equals(username, loginPostDto.username) && Objects.equals(password, loginPostDto.password);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "LoginPostDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
