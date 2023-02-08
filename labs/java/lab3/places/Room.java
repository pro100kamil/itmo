package places;

public class Room extends Place {
	protected String name;
	
	public Room(String name) {
		this.name = name;
	} 
	
	@Override
	public String toString() {
		return "Room " + name;
	}
}