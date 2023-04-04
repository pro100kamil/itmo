package managers;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Класс для разных проверок (является ли строка числом, командой и тд)
 */
public class ValidateManager {
    /**
     * Проверяет, является ли строка действительным числом
     *
     * @param value строка, которую проверяем
     * @return boolean true - является, false - не является
     */
    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Проверяет, является ли строка целым числом
     *
     * @param value строка, которую проверяем
     * @return boolean true - является, false - не является
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Проверяет, есть ли файл с таким названием
     *
     * @param value строка, которую проверяем
     * @return boolean true - является, false - не является
     */
    public static boolean isFile(String value) {
        return Files.exists(Path.of(value));
    }
}