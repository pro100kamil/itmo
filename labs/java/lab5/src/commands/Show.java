package commands;

import managers.CollectionManager;

/**
 * Команда show
 */
public class Show extends Command {
	
	public Show() { 
		super("show", "выводит элементы коллекции");
	}
	
	/**Выводит элементы коллекции
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printElements();
	};
}
