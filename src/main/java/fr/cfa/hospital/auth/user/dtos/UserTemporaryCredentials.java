package fr.cfa.hospital.auth.user.dtos;

public class UserTemporaryCredentials {
    private String username;

    private String rawPassword;

    public UserTemporaryCredentials() {
    }

    public UserTemporaryCredentials(String username, String rawPassword) {
        this.username = username;
        this.rawPassword = rawPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    @Override
    public String toString() {
        return "UserTemporaryCredentials{" +
                "username='" + username + '\'' +
                ", rawPassword='" + rawPassword + '\'' +
                '}';
    }
}
