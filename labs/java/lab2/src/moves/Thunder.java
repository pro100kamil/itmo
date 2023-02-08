package moves;

import ru.ifmo.se.pokemon.*;

public class Thunder extends SpecialMove {
	public Thunder() {
		super(Type.ELECTRIC, 10, 70 / 100);
	}
	
	@Override 
	protected void applySelfEffects(Pokemon p) {
		Effect ef = new Effect().chance(0.3);
		ef.paralyze(p);
	}
	
	@Override
	protected String describe() {
		return "Thunder";
	}
}