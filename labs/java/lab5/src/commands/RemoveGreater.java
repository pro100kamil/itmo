package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� remove_greater
 */
public class RemoveGreater extends Command {
	private Worker elem;

	public RemoveGreater(Worker elem) {
		super("remove", "������� ����������, ������� ������ ���������");
		this.elem = elem;
	}

	/**
	 * ������� ����������, ������� ������ ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.removeGreater(elem);
	};
}
