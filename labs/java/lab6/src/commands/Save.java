package commands;

import exceptions.WrongCommandArgsException;
import managers.*;

/**
 * Команда save.
 * Сохраняет коллекцию в файл.
 */
public class Save extends Command {
    private final String fileName;
    private final CollectionManager collectionManager;

    public Save(String fileName, CollectionManager collectionManager, Console console) {
        super("save", "сохраняет коллекцию в файл", console);
        this.fileName = fileName;
        this.collectionManager = collectionManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromLinkedListWorker(collectionManager.getLinkedList()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
