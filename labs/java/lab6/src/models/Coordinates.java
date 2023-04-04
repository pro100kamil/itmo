package models;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private final Integer x; //Поле не может быть null
    private final Integer y; //Поле не может быть null
	
	public Coordinates(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public Integer getX() {
		return x;
	}
	
	public Integer getY() {
		return y;
	}

	public boolean validate() {
		return x != null && y != null;
	}

	@Override
	public String toString() {
		return "Coordinates{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}