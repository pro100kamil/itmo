package commands;

import models.Worker;
import managers.CollectionManager;
import exceptions.NotUniqueIdException;

/**
 * Команда add
 */
public class Add extends Command {
	private Worker elem;

	public Add(Worker elem) {
		super("add", "добавляет элемент в коллекцию");
		this.elem = elem;
	}

	/**
	 * Добавляет работника в коллекцию
	 * @param collectionManager менеджер коллекции, отвечающий за коллекцию, в которую мы добавляем
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
