package commands;

import managers.CollectionManager;

/**
 * ������� show
 */
public class Show extends Command {
	
	public Show() { 
		super("show", "������� �������� ���������");
	}
	
	/**������� �������� ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printElements();
	};
}
