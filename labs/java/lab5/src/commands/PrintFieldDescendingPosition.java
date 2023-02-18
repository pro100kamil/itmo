package commands;

import managers.CollectionManager;

/**
 * Команда printFieldDescendingPostion
 */
public class PrintFieldDescendingPosition extends Command {
	private Command[] PrintFieldDescendingPosition;
	
	public PrintFieldDescendingPosition() { 
		super("printFieldDescendingPosition", "выводит значения поля position всех элементов в порядке убывания");
	}

	/**
	 * Выводит значения поля position всех элементов в порядке убывания
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printFieldDescendingPosition();
	};
}
