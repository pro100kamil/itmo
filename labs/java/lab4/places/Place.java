package places;

import java.util.*;
import things.*;
import people.*;

public abstract class Place implements AddablePerson, AddableThing {
	private ArrayList<Thing> things = new ArrayList<>();
	private ArrayList<Person> people = new ArrayList<>();
	
	abstract public String getName();
	
	@Override
	public void addThings(Thing ... things) {
		for (Thing th : things) {
			this.things.add(th);
		}
	}
	
	@Override
	public void addPeople(Person ... people) {
		for (Person p : people) {
			this.people.add(p);
		}
	}
	
	public Thing[] getThings() {
		int n = things.size();
		Thing[] ths = new Thing[n];
		for (int i = 0; i < n; i++) {
			ths[i] = things.get(i);
		}
		return ths;
	}
	
	public Person[] getPeople() {
		int n = people.size();
		Person[] ps = new Person[n];
		for (int i = 0; i < n; i++) {
			ps[i] = people.get(i);
		}
		return ps;
	}
	
	@Override
	public String toString() {
		return getName();
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
		Place otherPlace = (Place) other;
		return toString().equals(otherPlace.toString());
	}
}