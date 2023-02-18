package commands;

import models.Worker;
import managers.CollectionManager;
import exceptions.NotUniqueIdException;

/**
 * ������� add
 */
public class Add extends Command {
	private Worker elem;

	public Add(Worker elem) {
		super("add", "��������� ������� � ���������");
		this.elem = elem;
	}

	/**
	 * ��������� ��������� � ���������
	 * @param collectionManager �������� ���������, ���������� �� ���������, � ������� �� ���������
	 */
	public void execute(CollectionManager collectionManager) {
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
