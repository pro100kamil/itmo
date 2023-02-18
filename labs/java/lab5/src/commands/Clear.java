package commands;

import managers.CollectionManager;

/**
 * Команда clear
 */
public class Clear extends Command {
		
	public Clear() { 
		super("clear", "очищает коллекцию");
	}

	/**
	 * Очищает коллекцию
	 * @param collectionManager менеджер коллекции, отвечающий за коллекцию, которую мы очистим
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.clear();
	};
}
