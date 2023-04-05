package common.commands;

import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import client.managers.ValidateManager;
import common.models.Worker;

/**
 * Команда update.
 * Обновляет работника по id на основе заданного работника.
 */
public class Update extends CommandWithWorker {
    private Worker worker;

    public Update() {
        super("update", "обновляет работника по id на основе заданного работника");
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
    }

    @Override
    public void execute(String[] args) {
        //TODO сделать серверную валидацию
        collectionManager.update(Integer.parseInt(args[0]), worker);
    }
}
