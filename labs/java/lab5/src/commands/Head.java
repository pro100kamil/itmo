package commands;

import managers.CollectionManager;

/**
 * ������� head
 */
public class Head extends Command {
	private CollectionManager collectionManager;
			
	public Head(CollectionManager collectionManager) { 
		super("head", "������� ������ ������� ���������");
		this.collectionManager = collectionManager;
	}

	/**
	 * ������� ������ ������� ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute() {
		if (collectionManager.isEmpty()) {
			System.out.println("��������� ������, ������� ��� ������� ��������!");
		}
		else {
			System.out.println(collectionManager.getHead());
		}
	};
}
