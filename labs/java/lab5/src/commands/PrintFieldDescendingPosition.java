package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;

/**
 * Команда printFieldDescendingPostion
 */
public class PrintFieldDescendingPosition extends Command {
    private CollectionManager collectionManager;

    public PrintFieldDescendingPosition(CollectionManager collectionManager) {
        super("print_field_descending_position", "выводит значения поля position всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит значения поля position всех элементов в порядке убывания
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.printFieldDescendingPosition();
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }
    }

    ;
}
