package common.consoles;

import common.consoles.Console;

import java.util.Scanner;

/**
 * Класс для работы со стандартным вводом
 */
public class StandardConsole implements Console {
    private final Scanner sc = new Scanner(System.in);


    public boolean hasNext() {
        return true;
    }

    public String getNextStr() {
        return sc.nextLine().strip();
    }

    public void write(String text) {
        System.out.println(text);
    }
}