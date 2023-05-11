package common.consoles;

import common.managers.FileManager;

/**
 * Класс для работы с файловым вводом
 */
public class FileConsole implements Console {
    private final String[] lines;
    private int curInd = 0;

    public FileConsole(String fileName) {
        String text = FileManager.getTextFromFile(fileName);
        lines = text.split("\n");
    }

    public String[] getLines() {
        return lines;
    }

    @Override
    public boolean hasNext() {
        return curInd < lines.length;
    }

    @Override
    public String getNextStr() {
        return lines[curInd++].strip();
    }

    @Override
    public void write(String text) {
        System.out.println(text);
    }
}