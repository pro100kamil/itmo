package commands;

import managers.*;

/**
 * Команда save
 */
public class Save extends Command {
	private String fileName = "files/data.json";
	
	public Save() { 
		super("save", "сохраняет коллекцию в файл");
	}

	/**
	 * Сохраняет коллекцию в файл
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromLinkedListWorker(collectionManager.getLinkedList()));
	};
}
