package commands;

import managers.CollectionManager;

/**
 * ������� remove_by_id
 */
public class Remove extends Command {
	private Integer id;

	public Remove(Integer id) {
		super("remove_by_id", "������� ��������� �� id �� ���������");
		this.id = id;
	}

	/**
	 * ������� ��������� �� id �� ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.remove(id);
	};
}
