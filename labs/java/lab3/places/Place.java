package places;

import java.util.*;
import things.*;

public class Place implements AddablePerson, AddableThing {
	protected ArrayList<Object> things = new ArrayList<>();
	protected ArrayList<Object> people = new ArrayList<>();
	
	@Override
	public void addThings(Object ... things) {
		for (Object th : things) {
			this.things.add(th);
		}
	}
	
	@Override
	public void addPeople(Object ... people) {
		for (Object p : people) {
			this.people.add(p);
		}
	}
	
	public Object[] getThings() {
		return things.toArray();
	}
	
	public Object[] getPeople() {
		return people.toArray();
	}
	
	@Override
	public String toString() {
		return "Place";
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