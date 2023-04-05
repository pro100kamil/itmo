package common.exceptions;

/**
 * Исключение выбрасывается, когда пытаются вызвать команду с неправильными аргументами
 */
public class WrongCommandArgsException extends Exception {
	@Override
	public String toString() {
		return "Неправильные аргументы команды!";
	}
}