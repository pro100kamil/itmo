package commands;

import managers.CollectionManager;

/**
 * Команда head
 */
public class Head extends Command {
			
	public Head() { 
		super("head", "выводит первый элемент коллекции");
	}

	/**
	 * Выводит первый элемент коллекции
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		if (collectionManager.isEmpty()) {
			System.out.println("Коллекция пустая, поэтому нет первого элемента!");
		}
		else {
			System.out.println(collectionManager.getHead());
		}
	};
}
