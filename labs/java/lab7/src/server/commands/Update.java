package server.commands;

import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import common.managers.ValidateManager;

/**
 * Команда update.
 * Обновляет работника по id на основе заданного работника.
 */
public class Update extends ServerCommand {

    public Update() {
        super("update", "обновляет работника по id на основе заданного работника",
                true, true);
    }

    @Override
    public void validateArgs(String[] args) throws NonExistentId, WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            collectionManager.update(Integer.parseInt(args[0]), worker);
        } catch (WrongCommandArgsException | NonExistentId e) {
            console.write(e.toString());
        }
    }
}
