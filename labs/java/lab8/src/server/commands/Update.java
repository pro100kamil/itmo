package server.commands;

import common.exceptions.NonExistentId;
import common.exceptions.UnavailableModelException;
import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongModelsException;
import common.managers.ValidateManager;
import common.models.Worker;

import java.sql.SQLException;

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
    public void validateArgs(String[] args) throws NonExistentId, WrongCommandArgsException, UnavailableModelException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
        Worker oldWorker = collectionManager.getWorkerById(Integer.parseInt(args[0]));
        if (oldWorker.getCreatorId() != user.getId()) { //если у модельки другой создатель
            throw new UnavailableModelException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            //валидация моделек
            if (worker == null || !worker.validate()) {
                throw new WrongModelsException();
            }
            //обновляем в бд
            int count = workerDatabaseManager.updateWorker(user, Integer.parseInt(args[0]), worker);
            if (count == 0) {
                throw new UnavailableModelException();
            }
            //обновляем в коллекции
            collectionManager.update(Integer.parseInt(args[0]), worker);
        } catch (WrongCommandArgsException | NonExistentId | UnavailableModelException e) {
            console.write(e.toString());
        }catch (SQLException e) {
            console.write(e.toString());
            console.write("Обновить работника не получилось");
        }
    }
}
