package common.exceptions;

/**
 * Исключение выбрасывается, когда пользователь пытает удалить
 * или обновить чужую модельку (чужого работника).
 */
public class UnavailableModelException extends Exception {
    @Override
    public String toString() {
        return "Этого работника создал другой пользователь!";
    }
}