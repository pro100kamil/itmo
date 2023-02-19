package managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

/**
 * ����� ��� ������ � �������. ������ � ���, ���������� �� ���.
 */
public class FileManager {
    /**
     * �������� ����� �� �����
     *
     * @param fileName ��� �����
     * @return String ����� �� �����
     */
    public static String getTextFromFile(String fileName) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
            String s = "";
            int curB = inputStreamReader.read();
            while (curB != -1) {
                s += String.valueOf((char) curB);
                curB = inputStreamReader.read();
            }
            return s;
        } catch (IOException e) {
            return "������ �����-������";
        }
    }

    /**
     * ���������� ����� � ����
     *
     * @param fileName ��� �����
     * @param text     ����� ��� �����
     */
    public static void writeTextToFile(String fileName, String text) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName));
            char[] chars = text.toCharArray();
            for (char c : chars) {
                outputStreamWriter.write(c);
            }
            outputStreamWriter.close();
        } catch (IOException e) {
            System.out.println("������ �����-������");
        }
    }
}