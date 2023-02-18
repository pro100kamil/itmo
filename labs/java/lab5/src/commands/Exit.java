package commands;

import managers.CollectionManager;

/**
 * Команда exit
 */
public class Exit extends Command {
		
	public Exit() { 
		super("exit", "завершает программу");
	}
	
	/**
	 * Завершает программу
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		System.exit(0);
	};
}
