package server.commands;

import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongModelsException;

/**
 * Команда remove_greater.
 * Удаляет работников, которые больше заданного.
 */
public class RemoveGreater extends ServerCommand {

    public RemoveGreater() {
        super("remove_greater", "удаляет работников, которые больше заданного",
        true, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
        //валидация моделек
        if (worker == null || !worker.validate()) {
            throw new WrongModelsException();
        }
    }

    public void execute(String[] args) {
        try {
            validateArgs(args);
            collectionManager.removeGreater(worker);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
