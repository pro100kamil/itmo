package moves;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
	public Swagger() {
		super(Type.NORMAL, 0, 85 / 100);
	}
	
	@Override
	protected void applyOppEffects(Pokemon p) {
		Effect.confuse(p);
	}
	
	@Override
	protected void applySelfEffects(Pokemon p) {
		double curAt = p.getStat(Stat.ATTACK);
		Effect ef = new Effect().stat(Stat.ATTACK, (int)curAt + 2);
		p.addEffect(ef);
	}
	
	@Override
	protected String describe() {
		return "Swagger";
	}
}