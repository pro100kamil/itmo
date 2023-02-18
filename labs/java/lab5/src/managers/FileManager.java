package managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

/**
 * Класс для работы с файлами. Запись в них, считывание из них.
 */
public class FileManager {
	public static String getFromFile(String fileName) {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
			String s = "";
			int curB = inputStreamReader.read();
			while (curB != -1) {
				s += String.valueOf((char)curB);
				curB = inputStreamReader.read();
			}
			return s;			
		}
		catch (IOException e) {
			return "ошибка ввода-вывода";
		}
	}
	
	public static void writeTextToFile(String fileName, String text) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName));
			char[] chars = text.toCharArray();
			for (char c : chars) {
				outputStreamWriter.write(c);
			}
			outputStreamWriter.close();
		}
		catch (IOException e) {
			System.out.println("ошибка ввода-вывода");
		}
	}
}