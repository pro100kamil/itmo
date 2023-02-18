package commands;

import managers.CollectionManager;

/**
 * Команда remove_by_id
 */
public class Remove extends Command {
	private Integer id;

	public Remove(Integer id) {
		super("remove_by_id", "удаляет работника по id из коллекции");
		this.id = id;
	}

	/**
	 * Удаляет работника по id из коллекции
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.remove(id);
	};
}
