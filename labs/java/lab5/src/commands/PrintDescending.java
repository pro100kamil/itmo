package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * Команда print_descending
 */
public class PrintDescending extends Command {

	public PrintDescending() {
		super("print_descending", "выводит элементы коллекции в порядке убывания");
	}

	/**
	 * Выводит элементы коллекции в порядке убывания
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printDescending();
	};
}
