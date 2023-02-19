package exceptions;

/**
 * Исключение выбрасывается, когда используется неуникальный id
 */
public class NotUniqueIdException extends Exception {
    @Override
    public String toString() {
        return "Такой id уже существует";
    }
}