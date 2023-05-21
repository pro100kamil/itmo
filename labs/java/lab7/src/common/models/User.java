package common.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private final String name;
    private final String password;
    private String role = "user_min";

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean validate() {
        return getName().length() < 40;
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
               '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
