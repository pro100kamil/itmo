package moves;

import ru.ifmo.se.pokemon.*;

public class Recover extends StatusMove {
	public Recover() {
		super(Type.NORMAL, 0, 100 / 100);
	}
	
	@Override
	protected void applySelfEffects(Pokemon p) {
		Effect ef = new Effect().stat(Stat.HP, 50);
		p.addEffect(ef);
	}
	
	@Override
	protected String describe() {
		return "Recover";
	}
}