package commands;

import managers.CollectionManager;

/**
 * ������� printFieldDescendingPostion
 */
public class PrintFieldDescendingPosition extends Command {
	private Command[] PrintFieldDescendingPosition;
	
	public PrintFieldDescendingPosition() { 
		super("printFieldDescendingPosition", "������� �������� ���� position ���� ��������� � ������� ��������");
	}

	/**
	 * ������� �������� ���� position ���� ��������� � ������� ��������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printFieldDescendingPosition();
	};
}
