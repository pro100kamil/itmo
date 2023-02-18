package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * Команда remove_greater
 */
public class RemoveGreater extends Command {
	private Worker elem;

	public RemoveGreater(Worker elem) {
		super("remove", "удаляет работников, которые больше заданного");
		this.elem = elem;
	}

	/**
	 * Удаляет работников, которые больше заданного
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.removeGreater(elem);
	};
}
