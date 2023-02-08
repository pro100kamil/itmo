package people;

import places.*;

public class Person {
	//место, где находится человек (комната, улица)
	protected Object place;
	protected Status status = Status.DEFAULT;
	
	public void changePlace(Object place) {
		this.place = place;
		System.out.printf("%s goes %s\n", toString(), place.toString());
	}
	
	public void changeStatus(Status status) {
		this.status = status;
		System.out.printf("%s is %s\n", toString(), status.toString());
	}
	
	public void go(Object ob) {
		System.out.printf("%s goes to %s\n", toString(), ob.toString());
	}
	
	public void stop() {
		System.out.printf("%s stops\n", toString());
	}
	
	public void say(String text) {
		System.out.printf("%s says '%s'\n", toString(), text);
	}
	
	public void want(String text) {
		System.out.printf("%s wants to %s\n", toString(), text);
	}
	
	public void talk(Person ... people) {
		String res = toString() + " talks";
		int i = 0;
		for (Person person : people) {
			if (i == 0) res += " with ";
			res += person;
			if (i != people.length - 1) res += ", ";
			i++;
		}
		System.out.println(res);
	}
	
	@Override 
	public String toString() {
		return "Default person";
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