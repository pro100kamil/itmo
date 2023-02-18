package managers;

/**
 * ��������� ��� "��������". ������ ������� - ��� ����������� ����/�����. ������ ������� - ��� ���������� �� ���� � ����������� �����.
 */
public interface Console {
	/**
	 * ���������, ����� �� ����� �������� ��������� ������
	 * @return boolean ����� �� �����
	 */
	public boolean hasNext();
	
	/**
	 * �������� ��������� ������
	 * @return String ��������� ������
	 */
	public String getNextStr();
	
	/**
	 * ������ ������ ������
	 * @param text �����
	 */
	public void write(String text);
}