package common.consoles;

/**
 * Класс для работы со строковой консолью.
 * Ввода нет.
 * Весь вывод накапливается в массиве строк.
 */
public class StringConsole implements Console {
    private String res = "";

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public String getNextStr() {
        return null;
    }

    @Override
    public void write(String text) {
        res += text + "\n";
    }

    public String getAllText() {
        return res.strip();
    }
}