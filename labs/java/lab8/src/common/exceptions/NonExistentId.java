package common.exceptions;

/**
 * Исключение выбрасывается, когда используется несуществующий id.
 * Например, мы пытаемся обновить работника с несуществующим id.
 */
public class NonExistentId extends Exception {
    @Override
    public String toString() {
        return "Такого id не существует";
    }
}
