package common.managers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Класс для разных проверок (является ли строка числом, корректным файлом и тд)
 */
public class ValidateManager {
    /**
     * Проверяет, является ли строка енамом
     *
     * @param value - проверяемая строка
     * @return boolean true - является, false - не является
     */
    public static <T extends Enum<T>> boolean isEnum(String value, Class<T> cl) {
        try {
            Enum.valueOf(cl, value);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

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
     * Проверяет, является ли строка датой (класс LocalDate)
     *
     * @param value строка, которую проверяем
     * @return boolean true - является, false - не является
     */
    public static boolean isDate(String value) {
        try {
            LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Проверяет, является ли строка датой со временем (класс LocalDateTime)
     *
     * @param value строка, которую проверяем
     * @return boolean true - является, false - не является
     */
    public static boolean isDateTime(String value) {
        try {
            LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
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