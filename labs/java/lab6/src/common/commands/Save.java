package common.commands;

import common.exceptions.WrongCommandArgsException;
import server.managers.FileManager;
import server.managers.JsonManager;

/**
 * Команда save.
 * Сохраняет коллекцию в файл.
 */
public class Save extends Command {
    public Save() {
        super("save", "сохраняет коллекцию в файл");
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
            FileManager.writeTextToFile(dataFileName, JsonManager.getStrJsonFromLinkedListWorker(collectionManager.getLinkedList()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
