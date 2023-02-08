package moves;

import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove {
	public AerialAce() {
		super(Type.FLYING, 60, 100 / 100);
	}
	
	@Override
	protected String describe() {
		return "AerialAce";
	}
}