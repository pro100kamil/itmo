package places;

public class LivingRoom extends Room {
	public LivingRoom() {
		super("Default LivingRoom");
	} 
	
	public LivingRoom(String name) {
		super(name);
	} 
	
	@Override
	public String toString() {
		return "LivingRoom " + name;
	}
}