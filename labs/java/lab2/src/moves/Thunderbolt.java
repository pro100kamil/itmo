package moves;

import ru.ifmo.se.pokemon.*;

public class Thunderbolt extends SpecialMove {
	public Thunderbolt() {
		super(Type.ELECTRIC, 90, 100 / 100);
	}
	
	@Override
	protected void applyOppEffects(Pokemon p) {
		Effect ef = new Effect().chance(0.1);
		ef.paralyze(p);
	}
	
	@Override
	protected String describe() {
		return "Thunderbolt";
	}
}