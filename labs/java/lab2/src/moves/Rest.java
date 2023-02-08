package moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
	public Rest() {
		super(Type.PSYCHIC, 0, 100 / 100);
	}
	
	@Override
	protected void applySelfEffects(Pokemon p) {
		Effect ef = new Effect().condition(Status.SLEEP).turns(2).stat(Stat.HP, 100);
		p.addEffect(ef);
	}
	
	@Override
	protected String describe() {
		return "Rest";
	}
}