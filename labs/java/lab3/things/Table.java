package things;

import java.util.*;

public class Table extends Thing implements AddableThing {
	protected ArrayList<Object> things = new ArrayList<>();
	
	@Override
	public void addThings(Object ... things) {
		for (Object th : things) {
			this.things.add(th);
		}
	}
	
	@Override
	public String getName() {
		return "Table";
	}
}