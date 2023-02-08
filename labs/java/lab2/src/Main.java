import ru.ifmo.se.pokemon.*;
import pokemons.*;
import moves.*;

public class Main {
	public static void main(String args[]) {
		Battle b = new Battle();
		Celebi celebi = new Celebi("celebi", 1);
		Amaura amaura = new Amaura("amaura", 1);
		Aurorus aurorus = new Aurorus("aurorus", 1);
		Flabebe flabebe = new Flabebe("flabebe", 1);
		Floette floette = new Floette("floette", 1);
		Floette florges = new Florges("florges", 1);
		
		b.addAlly(celebi);
		b.addAlly(amaura);
		b.addAlly(aurorus);
		
		b.addFoe(flabebe);
		b.addFoe(floette);
		b.addFoe(florges);
		b.go();
	}
}




// : - вместо ; на unix
//javac -cp Pokemon.jar -d classes *.java pokemons/*.java moves/*.java
//javac -cp (путь до Pokemon.jar) -d classes *.java pokemons/*.java moves/*.java
//java -cp classes;Pokemon.jar Main
//jar -cfm Main.jar Manifest.txt -C classes .
//java -jar Main.jar

//jar -cfm Main.jar Manifest.txt -C classes/*.class classes/pokemons/*.class classes/moves/*.class
//scp -r -P 2222 src s367149@helios.cs.ifmo.ru:/home/studs/s367149/labs/proga/lab2
//scp -P 2222 s367149@helios.cs.ifmo.ru:~/labs/proga/lab2/src/Main.java Main1.java