package common.exceptions;

/**
 * Исключение выбрасывается, когда хотят зарегистрировать новый аккаунт с уже занятым логином
 */
public class NonUniqueLoginException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "loginAlreadyExists";
//        return "Это имя пользователя занято!";
    }
}
