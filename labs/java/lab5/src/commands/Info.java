package commands;

import managers.CollectionManager;

/**
 * ������� info
 */
public class Info extends Command {
	private CollectionManager collectionManager;
	
	public Info() {
		super("info", "������� ���������� � ���������");
	}
	
	/**������� ���������� � ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printInfo();
	};
}
