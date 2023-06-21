package server.models;

import common.models.UserRole;

public class ServerUser {
    private final int id;
    private final String name;
    private final String passwordDigest;
    private final String salt;
    private final UserRole role;

    public ServerUser(int id, String name, String passwordDigest, String salt, UserRole role) {
        this.id = id;
        this.name = name;
        this.passwordDigest = passwordDigest;
        this.salt = salt;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public String getSalt() {
        return salt;
    }

    public UserRole getRole() {
        return role;
    }
}
