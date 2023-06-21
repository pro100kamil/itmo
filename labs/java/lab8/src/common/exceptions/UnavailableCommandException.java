package common.exceptions;

/**
 * Исключение выбрасывается, когда незарегистрированный пользователь пытается выполнить команду,
 * которая только для зарегистрированных пользователей.
 */
public class UnavailableCommandException  extends Exception {
    @Override
    public String toString() {
        return "Эта команда только для зарегистрированных пользователей!";
    }
}
