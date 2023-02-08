package moves;

import ru.ifmo.se.pokemon.*;

public class CalmMind extends StatusMove {
	public CalmMind() {
		super(Type.PSYCHIC, 0, 100 / 100);
	}
	
	@Override 
	protected void applySelfEffects(Pokemon p) {
		double curSpAtt = p.getStat(Stat.SPECIAL_ATTACK);
		double curSpDef = p.getStat(Stat.SPECIAL_DEFENSE);
		Effect ef = new Effect().stat(Stat.SPECIAL_ATTACK, (int)curSpAtt + 1).stat(Stat.SPECIAL_DEFENSE, (int)curSpDef + 1);
		p.addEffect(ef);
	}
	
	@Override
	protected String describe() {
		return "CalmMind";
	}
}