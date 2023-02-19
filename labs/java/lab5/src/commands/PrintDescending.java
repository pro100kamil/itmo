package commands;

import managers.CollectionManager;

/**
 * Команда print_descending
 */
public class PrintDescending extends Command {
    private CollectionManager collectionManager;

    public PrintDescending(CollectionManager collectionManager) {
        super("print_descending", "выводит элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит элементы коллекции в порядке убывания (работники сортируются по размеру зарплаты)
     */
    public void execute() {
        try {
            collectionManager.printDescending();
        } catch (NullPointerException e) {

        }
    }

}
