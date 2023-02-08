package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Floette extends Flabebe {
	public Floette(String name, int level) {
		super(name, level);
		setStats(54, 45, 47, 75, 98, 52);
		setType(Type.FAIRY);
		setMove(new Facade(), new CalmMind(), new RazorLeaf());
	}
}