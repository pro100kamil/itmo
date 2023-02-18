package commands;

import managers.CollectionManager;

/**
 * ������� head
 */
public class Head extends Command {
			
	public Head() { 
		super("head", "������� ������ ������� ���������");
	}

	/**
	 * ������� ������ ������� ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		if (collectionManager.isEmpty()) {
			System.out.println("��������� ������, ������� ��� ������� ��������!");
		}
		else {
			System.out.println(collectionManager.getHead());
		}
	};
}
