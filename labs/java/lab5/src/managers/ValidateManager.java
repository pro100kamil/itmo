package managers;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 *  ласс дл¤ разных проверок (¤вл¤етс¤ ли строка числом, командой и тд)
 */
public class ValidateManager {
	/**
     * ѕровер¤ет, ¤вл¤етс¤ ли строка действительным числом
	 * @param value строка, которую провер¤ем
	 * @return boolean true - ¤вл¤етс¤, false - не ¤вл¤етс¤
     */
	public static boolean isFloat(String value) {
		try {
			Float tmp = Float.parseFloat(value);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
     * ѕровер¤ет, ¤вл¤етс¤ ли строка целым числом
	 * @param value строка, которую провер¤ем
	 * @return boolean true - ¤вл¤етс¤, false - не ¤вл¤етс¤
     */
	public static boolean isInteger(String value) {
		try {
			Integer tmp = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
     * ѕровер¤ет, есть ли файл с таким названием
	 * @param value строка, которую провер¤ем
	 * @return boolean true - ¤вл¤етс¤, false - не ¤вл¤етс¤
     */
	public static boolean isFile(String value) {
		return Files.exists(Path.of(value));
	}
}