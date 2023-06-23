package common.exceptions;

/**
 * Исключение выбрасывается, когда происходит ошибка при входе (неправильный логин или пароль)
 */
public class WrongCredentialsException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "invalidCredentials";
//        return "Ошибка в логине или пароле!";
    }
}
