package commands;

import managers.CollectionManager;

/**
 * ������� clear
 */
public class Clear extends Command {
	private CollectionManager collectionManager;
		
	public Clear(CollectionManager collectionManager) { 
		super("clear", "������� ���������");
		this.collectionManager = collectionManager;
	}

	/**
	 * ������� ���������
	 */
	public void execute() {
		collectionManager.clear();
	};
}
