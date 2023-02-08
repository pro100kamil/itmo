package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Florges extends Floette {
	public Florges(String name, int level) {
		super(name, level);
		setStats(78, 65, 68, 112, 154, 75);
		setType(Type.FAIRY);
		setMove(new Facade(), new CalmMind(), new RazorLeaf(), new PetalBlizzard());
	}
}