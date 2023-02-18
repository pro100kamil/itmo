package commands;

import managers.CollectionManager;

/**
 * ������� clear
 */
public class Clear extends Command {
		
	public Clear() { 
		super("clear", "������� ���������");
	}

	/**
	 * ������� ���������
	 * @param collectionManager �������� ���������, ���������� �� ���������, ������� �� �������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.clear();
	};
}
