package common.consoles;

import common.managers.FileManager;

/**
 * Чтение из файла запись в строку
 */
public class FileStringConsole extends StringConsole {
    private final String[] fileLines;
    private int curInd = 0;

    public FileStringConsole(String fileName) {
        String text = FileManager.getTextFromFile(fileName);
        fileLines = text.split("\n");
    }

    public String[] getLines() {
        return fileLines;
    }

    @Override
    public boolean hasNext() {
        return curInd < fileLines.length;
    }

    @Override
    public String getNextStr() {
        return fileLines[curInd++].strip();
    }
}
