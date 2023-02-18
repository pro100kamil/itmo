package managers;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ������ ��� ������ �������� (�������� �� ������ ������, �������� � ��)
 */
public class ValidateManager {
	/**
     * ���������, �������� �� ������ �������������� ������
	 * @param value ������, ������� ��������
	 * @return boolean true - ��������, false - �� ��������
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
     * ���������, �������� �� ������ ����� ������
	 * @param value ������, ������� ��������
	 * @return boolean true - ��������, false - �� ��������
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
     * ���������, ���� �� ���� � ����� ���������
	 * @param value ������, ������� ��������
	 * @return boolean true - ��������, false - �� ��������
     */
	public static boolean isFile(String value) {
		return Files.exists(Path.of(value));
	}
}