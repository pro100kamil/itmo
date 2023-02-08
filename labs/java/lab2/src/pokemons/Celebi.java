package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Celebi extends Pokemon {
	public Celebi(String name, int level) {
		super(name, level);
		setStats(100, 100, 100, 100, 100, 100);
		
		setType(Type.GRASS, Type.PSYCHIC);
		setMove(new Rest(), new Swagger(), new Recover(), new AerialAce());
	}
}