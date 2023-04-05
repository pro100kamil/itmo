package common.exceptions;

/**
 * Исключение выбрасывается, когда используется несуществующий id
 */
public class NonExistentId extends Exception {
    @Override
    public String toString() {
        return "Такой id не существует";
    }
}
