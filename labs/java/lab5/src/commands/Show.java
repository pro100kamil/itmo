package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда show
 */
public class Show extends Command {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager, Console console) {
        super("show", "выводит элементы коллекции", console);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит элементы коллекции
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.printElements();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
