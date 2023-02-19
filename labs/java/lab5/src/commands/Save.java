package commands;

import managers.*;

/**
 * Команда save
 */
public class Save extends Command {
    private String fileName;
    private CollectionManager collectionManager;

    public Save(String fileName, CollectionManager collectionManager) {
        super("save", "сохраняет коллекцию в файл");
        this.fileName = fileName;
        this.collectionManager = collectionManager;
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void execute() {
        try {
            FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromLinkedListWorker(collectionManager.getLinkedList()));
        } catch (NullPointerException e) {

        }
    }

}
