package common.commands;

import common.exceptions.WrongCommandArgsException;
import common.models.Worker;

/**
 * Команда remove_greater.
 * Удаляет работников, которые больше заданного.
 */
public class RemoveGreater extends CommandWithWorker {
    private Worker worker;

    public RemoveGreater() {
        super("remove_greater", "удаляет работников, которые больше заданного");
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    public void execute(String[] args) {
        collectionManager.removeGreater(worker);
    }
}
