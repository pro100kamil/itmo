package places;

import java.util.*;

public class Home {
	private ArrayList<Room> rooms = new ArrayList<>();
	private boolean silence = true;
	
	public Home(Room ... rooms) {
		addRoom(rooms);
	}
	
	public void addRoom(Room ... rooms) {
		for (Room room : rooms) {
			this.rooms.add(room);
		}
	}
	
	public void makeSilence() {
		System.out.printf("Silence in %s\n", toString());
		silence = true;
	}
		
	public void makeNoise() {
		System.out.printf("Noise in %s\n", toString());
		silence = false;
	}
	
	@Override
	public String toString() {
		String res = "Home";
		int i = 0;
		for (Room room : rooms) {
			if (i == 0) res += " (Rooms: ";
			res += room;
			if (i != rooms.size() - 1) res += ", ";
			else res += ")";
			i++;
		}
		return res;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (getClass() != other.getClass()) {
			return false;
		}
		return hashCode() == other.hashCode();
	}
}