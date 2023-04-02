package models;

import java.time.LocalDate;

public class Person {
	private LocalDate birthday; //Поле может быть null
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 7, Строка не может быть пустой, Поле может быть null
	
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

	@Override
	public String toString() {
		return "Person{" +
				"birthday=" + birthday +
				", height=" + height +
				", passportID='" + passportID + '\'' +
				'}';
	}
}