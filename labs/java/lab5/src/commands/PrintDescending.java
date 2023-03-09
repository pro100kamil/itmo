package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда print_descending
 */
public class PrintDescending extends Command {
    private CollectionManager collectionManager;

    public PrintDescending(CollectionManager collectionManager, Console console) {
        super("print_descending", "выводит элементы коллекции в порядке убывания", console);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит элементы коллекции в порядке убывания (работники сортируются по размеру зарплаты)
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.printDescending();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }

}
