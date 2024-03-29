package common.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private final String name;
    private final String password;
    private UserRole role = UserRole.USER_MIN;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public User(int id, String name, UserRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
        password = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", role='" + role + '\'' +
               '}';
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
