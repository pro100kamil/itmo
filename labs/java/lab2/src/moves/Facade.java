package moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
	public Facade() {
		super(Type.NORMAL, 70, 100 / 100);
	}
	
	@Override 
	protected void applySelfEffects(Pokemon p) {
		Status cond = p.getCondition();
		if (cond == Status.BURN || cond == Status.POISON || cond == Status.PARALYZE) {
			double curAt = p.getStat(Stat.ATTACK);
			Effect ef = new Effect().stat(Stat.ATTACK, (int)curAt * 2);
			p.addEffect(ef);
		}
	}
	
	@Override
	protected String describe() {
		return "Facade";
	}
}