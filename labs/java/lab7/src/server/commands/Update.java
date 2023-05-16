package server.commands;

import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongModelsException;
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
        if (collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
        //валидация что юзер удаляет свой
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            //валидация моделек
            if (worker == null || !worker.validate()) {
                throw new WrongModelsException();
            }
            collectionManager.update(Integer.parseInt(args[0]), worker, user);
        } catch (WrongCommandArgsException | NonExistentId e) {
            console.write(e.toString());
        }
    }
}
