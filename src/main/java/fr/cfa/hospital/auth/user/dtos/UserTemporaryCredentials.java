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

    @Override
    public String toString() {
        return "UserTemporaryCredentials{" +
                "username='" + username + '\'' +
                ", rawPassword='" + rawPassword + '\'' +
                '}';
    }
}
