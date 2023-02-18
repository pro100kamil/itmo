package commands;

import managers.*;

/**
 * ������� save
 */
public class Save extends Command {
	private String fileName = "files/data.json";
	
	public Save() { 
		super("save", "��������� ��������� � ����");
	}

	/**
	 * ��������� ��������� � ����
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromLinkedListWorker(collectionManager.getLinkedList()));
	};
}
