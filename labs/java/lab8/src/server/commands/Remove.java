package server.commands;

import common.exceptions.NonExistentId;
import common.exceptions.UnavailableModelException;
import common.exceptions.WrongCommandArgsException;
import common.managers.ValidateManager;
import common.models.Worker;

import java.sql.SQLException;

/**
 * Команда remove_by_id id.
 * Удаляет работника по id из коллекции.
 */
public class Remove extends ServerCommand {

    public Remove() {
        super("remove_by_id", "удаляет работника по id из коллекции",
                false, true);
    }

    @Override
    public void validateArgs(String[] args) throws NonExistentId, WrongCommandArgsException, UnavailableModelException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
        Worker worker = collectionManager.getWorkerById(Integer.parseInt(args[0]));
        if (worker.getCreatorId() != user.getId()) { //если у модельки другой создатель
            throw new UnavailableModelException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            //удаляем в бд
            int count = workerDatabaseManager.removeWorker(user, collectionManager.getWorkerById(Integer.parseInt(args[0])));
            if (count == 0) {
                throw new UnavailableModelException();
            }
            //удаляем в коллекции
            collectionManager.remove(Integer.parseInt(args[0]));
        } catch (WrongCommandArgsException | NonExistentId | UnavailableModelException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Удалить работника не получилось");
        }
    }
}
