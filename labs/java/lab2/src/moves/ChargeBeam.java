package moves;

import ru.ifmo.se.pokemon.*;

public class ChargeBeam extends SpecialMove {
	public ChargeBeam() {
		super(Type.ELECTRIC, 50, 90 / 100);
	}
	
	@Override
	protected void applySelfEffects(Pokemon p) {
		double curSpAt = p.getStat(Stat.SPECIAL_ATTACK);
		Effect ef = new Effect().stat(Stat.SPECIAL_ATTACK, (int)curSpAt + 1).chance(0.7);
		p.addEffect(ef);
	}
	
	@Override
	protected String describe() {
		return "ChargeBeam";
	}
}