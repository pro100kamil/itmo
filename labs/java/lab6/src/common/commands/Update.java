package common.commands;

import client.managers.ValidateManager;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;

/**
 * Команда update.
 * Обновляет работника по id на основе заданного работника.
 */
public class Update extends CommandWithWorker {
    public boolean ready;

    public Update() {
        super("update", "обновляет работника по id на основе заданного работника");
        ready = false;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    public void serverValidateArgs(String[] args) throws NonExistentId {
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
    }

    @Override
    public void execute(String[] args) {
        collectionManager.update(Integer.parseInt(args[0]), worker);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
