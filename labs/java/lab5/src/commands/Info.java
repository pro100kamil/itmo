package commands;

import managers.CollectionManager;

/**
 * ������� info
 */
public class Info extends Command {
	private CollectionManager collectionManager;
	
	public Info(CollectionManager collectionManager) {
		super("info", "������� ���������� � ���������");
		this.collectionManager = collectionManager;
	}
	
	/**
	 *������� ���������� � ���������
	 */
	public void execute() {
		collectionManager.printInfo();
	};
}
