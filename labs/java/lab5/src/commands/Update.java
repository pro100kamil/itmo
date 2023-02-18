package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� update
 */
public class Update extends Command {
	private Integer id;
	private	Worker elem;

	public Update(Integer id, Worker elem) {
		super("update", "��������� ��������� �� id �� ������ �������� ���������");
		this.id = id;
		this.elem = elem;
	}

	/**
	 * ��������� ��������� �� id �� ������ �������� ���������
	 * @param collectionManager �������� ���������, ���������� �� ���������, � ������� �� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		if (elem != null)
			collectionManager.update(id, elem);
	};
}
