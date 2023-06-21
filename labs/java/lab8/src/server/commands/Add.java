package server.commands;

import common.exceptions.NotUniqueIdException;
import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongModelsException;

import java.sql.SQLException;

/**
 * Команда add.
 * Добавляет работника в коллекцию.
 */
public class Add extends ServerCommand {

    public Add() {
        super("add", "добавляет элемент в коллекцию", true, true);
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);

            if (worker == null || !worker.validate() || worker.getId() != 0) {  //валидация моделек
                throw new WrongModelsException();
            }

            //добавляем в бд
            int id = workerDatabaseManager.addWorker(user, worker);
            worker.setId(id);
            worker.setCreatorId(user.getId());
            worker.getPerson().setCreatorId(user.getId());

            //добавляем в коллекцию
            collectionManager.add(worker);
        } catch (NotUniqueIdException | WrongCommandArgsException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Добавить работника не получилось");
        }
    }
}
