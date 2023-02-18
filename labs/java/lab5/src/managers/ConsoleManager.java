package managers;

import java.util.Scanner;

/**
 * Класс для работы со стандартным вводом
 */
public class ConsoleManager implements Console {
	private Scanner sc = new Scanner(System.in);
	
	
	public boolean hasNext() {
		return true;
	}
	
	public String getNextStr() {
		return sc.nextLine();
	}
	
	public void write(String text) {
		System.out.println(text);
	}
}