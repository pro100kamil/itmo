package managers;

public interface Console {
	public boolean hasNext();
	public String getNextStr();
	public void write(String text);
}