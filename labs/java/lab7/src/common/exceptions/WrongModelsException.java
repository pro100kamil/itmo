package common.exceptions;

/**
 * Исключение выбрасывается, когда на сервер передаётся некорректная модель.
 * А такое может быть только если на клиенте не обрабатывается проверка модели.
 */
public class WrongModelsException extends WrongCommandArgsException {
    @Override
    public String toString() {
        return "Некорректная модель!";
    }
}

