package places;

import java.util.*;

public class Home {
	private ArrayList<Room> rooms = new ArrayList<>();
	private boolean silence = true;

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
		if (this == other) return true;
		if (other == null || getClass() != other.getClass())
			return false;
		Home otherHome = (Home) other;
		return toString().equals(otherHome.toString());
	}
	
	public class Room extends Place {
		private String name;
		
		public Room(String name) {
			this.name = name;
			//Home.this.addRoom(this);
			addRoom(this);
		} 
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return "Room " + name;
		}
	}

	public class LivingRoom extends Room {
		public LivingRoom() {
			super("Default LivingRoom");
		} 
		
		public LivingRoom(String name) {
			super(name);
		} 
		
		@Override
		public String toString() {
			return "LivingRoom " + getName();
		}
	}
}