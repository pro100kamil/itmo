package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Flabebe extends Pokemon {
	public Flabebe(String name, int level) {
		super(name, level);
		setStats(44, 38, 39, 61, 79, 42);
		setType(Type.FAIRY);
		setMove(new Facade(), new CalmMind());
	}
}