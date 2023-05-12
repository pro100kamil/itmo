package common.exceptions;

/**
 * Исключение выбрасывается, когда происходит ошибка при регистрации или авторизации
 */
public class WrongCredentialsException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "Ошибка в логине или пароле!";
    }
}
