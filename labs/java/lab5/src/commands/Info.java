package commands;

import managers.CollectionManager;

/**
 * Команда info
 */
public class Info extends Command {
	private CollectionManager collectionManager;
	
	public Info() {
		super("info", "выводит информацию о коллекции");
	}
	
	/**Выводит информацию о коллекции
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printInfo();
	};
}
