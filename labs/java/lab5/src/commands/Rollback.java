package commands;

import exceptions.WrongCommandArgsException;
import managers.*;

public class Rollback extends Command {
    CollectionHistory collectionHistory;
    CollectionManager collectionManager;
    public Rollback(CollectionHistory collectionHistory, CollectionManager collectionManager, Console console) {
        super("rollback", "отменяет действие последних n комманд", console);
        this.collectionHistory = collectionHistory;
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {
        try {
            if (args.length != 1 || !ValidateManager.isInteger(args[0]) || Integer.parseInt(args[0]) < 0) {
                throw new WrongCommandArgsException();
            }
            int n = Integer.parseInt(args[0]);
            collectionHistory.rollback(n);
            collectionManager.setWorkers(collectionHistory.getCurState());
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
