package common.consoles;

/**
 * Класс для работы со строковой консолью.
 * Ввода нет.
 * Весь вывод накапливается в массиве строк.
 */
public class StringConsole implements Console {
    private String res = "";


    public boolean hasNext() {
        return false;
    }

    public String getNextStr() {
        return null;
    }

    public void write(String text) {
        res += text + "\n";
    }

    public String getAllText() {
        return res.strip();
    }
}