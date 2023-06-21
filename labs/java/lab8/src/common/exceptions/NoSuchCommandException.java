package common.exceptions;

/**
 * Исключение выбрасывается, когда пытаются вызвать несуществующую команду
 */
public class NoSuchCommandException extends Exception {
	@Override
	public String toString() {
		return "Несуществующая команда!";
	}
}