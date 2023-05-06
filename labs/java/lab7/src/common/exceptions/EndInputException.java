package common.exceptions;

/**
 * Исключение выбрасывается, когда ввод закончен, но ещё ожидается (например, из файла всё прочитано, но не все поля там были)
 */
public class EndInputException extends Exception {
	@Override
	public String toString() {
		return "Неполная информация!";
	}
}