package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда head
 */
public class Head extends Command {
    private CollectionManager collectionManager;

    public Head(CollectionManager collectionManager, Console console) {
        super("head", "выводит первый элемент коллекции", console);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит первый элемент коллекции
     *
     * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            if (collectionManager.isEmpty()) {
                console.write("Коллекция пустая, поэтому нет первого элемента!");
            } else {
                console.write(collectionManager.getHead().toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }

    }
}
