package managers;

/**
 * Класс для работы с файловым вводом
 */
public class FileConsole implements Console {
    private String fileName;
    private final String[] lines;
    private int curInd = 0;

    public FileConsole(String fileName) {
        this.fileName = fileName;
        String text = FileManager.getTextFromFile(fileName);
        lines = text.split("\n");
    }

    public boolean hasNext() {
        return curInd < lines.length;
    }

    public String getNextStr() {
        return lines[curInd++].strip();
    }

    public void write(String text) {
        System.out.println(text);
    }
}