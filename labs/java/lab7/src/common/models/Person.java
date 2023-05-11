package common.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private Integer id = 0; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    //id = 0 изначально, чтоб понимать, что ему не задали значение на сервере
    private final LocalDate birthday; //Поле может быть null
    private final float height; //Значение поля должно быть больше 0
    private final String passportID;
    //Длина строки должна быть не меньше 7, строка не может быть пустой, поле может быть null

    private int creatorId;

    public Person(LocalDate birthday, float height, String passportID) {
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
    }

    public boolean validate() {
        if (height <= 0) {
            return false;
        }
        if (passportID != null && (passportID.length() < 7 || passportID.isBlank())) {
            return false;
        }
        return true;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public float getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }

    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", birthday=" + birthday +
               ", height=" + height +
               ", passportID='" + passportID + '\'' +
               ", creatorId=" + creatorId +
               '}';
    }
}