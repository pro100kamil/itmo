package managers;

/**
 * ����� ��� ������ � �������� ������
 */
public class FileConsole implements Console {
	private String fileName;
	private String[] lines;
	private int curInd = 0;
	
	public FileConsole(String fileName) {
		this.fileName = fileName;
		String text = FileManager.getFromFile(fileName);
		lines = text.split("\n");
	}
	
	public boolean hasNext() {
		return curInd < lines.length;
	}
	
	public String getNextStr() {
		return lines[curInd++];
	}
	
	public void write(String text) {
		System.out.println(text);
	}
}