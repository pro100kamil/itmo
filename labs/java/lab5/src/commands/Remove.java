package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;
import managers.ValidateManager;

/**
 * Команда remove_by_id
 */
public class Remove extends Command {
    private CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager, Console console) {
        super("remove_by_id", "удаляет работника по id из коллекции", console);
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет работника по id из коллекции
     */
    public void execute(String[] args) {
        try {
            if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
                throw new WrongCommandArgsException();
            }
            int id = Integer.parseInt(args[0]);
            collectionManager.remove(id);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }


}
