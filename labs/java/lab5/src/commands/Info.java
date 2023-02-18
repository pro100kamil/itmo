package commands;

import managers.CollectionManager;

/**
 * Команда info
 */
public class Info extends Command {
	private CollectionManager collectionManager;
	
	public Info(CollectionManager collectionManager) {
		super("info", "выводит информацию о коллекции");
		this.collectionManager = collectionManager;
	}
	
	/**
	 *Выводит информацию о коллекции
	 */
	public void execute() {
		collectionManager.printInfo();
	};
}
