package commands;

import models.Worker;
import managers.CollectionManager;

/**
 *  оманда update
 */
public class Update extends Command {
	private Integer id;
	private	Worker elem;

	public Update(Integer id, Worker elem) {
		super("update", "обновл€ет работника по id на основе заданное работника");
		this.id = id;
		this.elem = elem;
	}

	/**
	 * ќбновл€ет работника по id на основе заданное работника
	 * @param collectionManager менеджер коллекции, отвечающий за коллекцию, в которую мы добавл€ем
	 */
	public void execute(CollectionManager collectionManager) {
		if (elem != null)
			collectionManager.update(id, elem);
	};
}
