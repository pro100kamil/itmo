package commands;

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
    public void execute() {
        try {
            collectionManager.printFieldDescendingPosition();
        } catch (NullPointerException e) {

        }
    }

    ;
}
