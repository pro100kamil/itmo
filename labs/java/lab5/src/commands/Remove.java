package commands;

import managers.CollectionManager;

/**
 * ������� remove_by_id
 */
public class Remove extends Command {
	private Integer id;
	private CollectionManager collectionManager;

	public Remove(Integer id, CollectionManager collectionManager) {
		super("remove_by_id", "������� ��������� �� id �� ���������");
		this.id = id;
		this.collectionManager = collectionManager;
	}

	/**
	 * ������� ��������� �� id �� ���������
	 */
	public void execute() {
		collectionManager.remove(id);
	};
}
