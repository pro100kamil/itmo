package commands;

import models.Worker;
import managers.CollectionManager;
import exceptions.NotUniqueIdException;

/**
 * ������� add
 */
public class Add extends Command {
	private Worker elem;
	private CollectionManager collectionManager;

	public Add(Worker elem, CollectionManager collectionManager) {
		super("add", "��������� ������� � ���������");
		this.elem = elem;
		this.collectionManager = collectionManager;
	}

	/**
	 * ��������� ��������� � ���������
	 */
	public void execute() {
		if (elem != null) {
			try {
				collectionManager.add(elem);
			}
			catch (NotUniqueIdException e) {
				System.out.println(e);
			}
		}
	};
}
