package things;

import java.util.*;

public class Table extends Thing implements AddableThing {
	private ArrayList<Thing> things = new ArrayList<>();
	
	@Override
	public void addThings(Thing ... things) {
		for (Thing th : things) {
			this.things.add(th);
		}
	}
	
	@Override
	public String getName() {
		return "Table";
	}
}