package commands;

import managers.CollectionManager;

/**
 * ������� exit
 */
public class Exit extends Command {
		
	public Exit() { 
		super("exit", "��������� ���������");
	}
	
	/**
	 * ��������� ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		System.exit(0);
	};
}
