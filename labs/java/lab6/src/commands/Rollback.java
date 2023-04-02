package commands;

import exceptions.WrongCommandArgsException;
import managers.*;

/**
 * Команда rollback n.
 * Отменяет действие последних n команд.
 */
public class Rollback extends Command {
    private final CollectionHistory collectionHistory;
    private final CollectionManager collectionManager;
    public Rollback(CollectionHistory collectionHistory, CollectionManager collectionManager, Console console) {
        super("rollback", "отменяет действие последних n команд", console);
        this.collectionHistory = collectionHistory;
        this.collectionManager = collectionManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0]) || Integer.parseInt(args[0]) < 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            collectionHistory.rollback(Integer.parseInt(args[0]));
            collectionManager.setWorkers(collectionHistory.getCurState());
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
