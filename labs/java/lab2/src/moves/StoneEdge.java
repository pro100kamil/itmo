package moves;

import ru.ifmo.se.pokemon.*;

public class StoneEdge extends PhysicalMove {
	public StoneEdge() {
		super(Type.ROCK, 100, 80 / 100);
	}
	
	@Override
	protected String describe() {
		return "StoneEdge";
	}
}