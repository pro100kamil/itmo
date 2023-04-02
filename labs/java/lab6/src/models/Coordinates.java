package models;

public class Coordinates {
    private Integer x; //Поле не может быть null
    private Integer y; //Поле не может быть null
	
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