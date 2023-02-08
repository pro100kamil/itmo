package moves;

import ru.ifmo.se.pokemon.*;

public class RazorLeaf extends PhysicalMove {
	public RazorLeaf() {
		super(Type.GRASS, 55, 95 / 100);
	}
	
	@Override 
	protected void applySelfEffects(Pokemon p) {
		Effect ef = new Effect().chance(0.3);
		ef.paralyze(p);
	}
	
	@Override
	protected String describe() {
		return "RazorLeaf";
	}
}