package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда info
 */
public class Info extends Command {
    private CollectionManager collectionManager;

    public Info(CollectionManager collectionManager, Console console) {
        super("info", "выводит информацию о коллекции", console);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит информацию о коллекции
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.printInfo();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }

    }

}
