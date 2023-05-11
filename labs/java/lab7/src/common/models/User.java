package common.models;

public class User {
    private int id;
    private final String name;
    private final String password;

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
                ", password='" + password + '\'' +
                '}';
    }
}
