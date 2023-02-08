package people;

import places.*;
import things.*;
import exceptions.*;

import java.lang.Math;

public class Person {
	private String name;  //имя
	private int hp = 100; //кол-во жизней от 0 до 100
	private Place place; //место, где находится человек (комната, улица)
	private Status status = Status.DEFAULT;  //текущее состояние
	private Weapon weapon;  //текущее оружие
	
	public Person() {
		name = "default person";
	}
	
	public Person(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void changePlace(Place place) {
		this.place = place;
		System.out.printf("%s goes %s\n", toString(), place.toString());
	}
	
	public void changeStatus(Status status) {
		this.status = status;
		System.out.printf("%s is %s\n", toString(), status.toString());
	}
	
	public void go(Object ... road) {
		System.out.printf("%s goes to %s\n", toString(), road[road.length - 1].toString());
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
	
	public void chill() {
		hp = Math.max(100, hp + 30);
	}
	
	public void moveThing(Thing thing) throws FewLivesException {
		if (hp <= 20) {
			throw new FewLivesException();
		}
		hp -= 20;
		System.out.printf("%s moves %s\n", toString(), thing.toString());
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass())
			return false;
		Person otherPerson = (Person) other;
		return name.equals(otherPerson.getName());
	}
	
	public void setWeapon(Weapon weapon) throws OldWeaponException {
		if (this.weapon != null && this.weapon.toString().equals(weapon.toString())) {
			throw new OldWeaponException();
		}
		this.weapon = weapon;
		System.out.println(this + " with " + this.weapon);
	}
	
	public static class Weapon {
		@Override 
		public String toString() {
			return "default weapon";
		}
	}
}