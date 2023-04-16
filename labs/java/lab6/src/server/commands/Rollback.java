package server.commands;

import common.exceptions.WrongCommandArgsException;
import common.managers.ValidateManager;

/**
 * Команда rollback n.
 * Отменяет действие последних n команд.
 */
public class Rollback extends Command {
    public Rollback() {
        super("rollback", "отменяет действие последних n команд");
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
