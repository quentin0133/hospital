package fr.cfa.hospital.auth.user.dtos;

import java.util.Objects;

public class ChangePasswordDto {
    private String idUser;

    private String currentPassword;

    private String newPassword;

    public ChangePasswordDto() {
    }

    public ChangePasswordDto(String idUser, String currentPassword, String newPassword) {
        this.idUser = idUser;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ChangePasswordDto that = (ChangePasswordDto) object;
        return Objects.equals(idUser, that.idUser) && Objects.equals(currentPassword, that.currentPassword) && Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, currentPassword, newPassword);
    }
}
