package server.commands;

import common.exceptions.WrongCommandArgsException;
import common.exceptions.WrongModelsException;
import common.models.Worker;

import java.sql.SQLException;
import java.util.LinkedList;

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
    }

    public void execute(String[] args) {
        try {
            validateArgs(args);
            //валидация моделек
            if (worker == null || !worker.validate()) {
                throw new WrongModelsException();
            }
            //удаляем в бд
            for (Worker other : new LinkedList<>(collectionManager.getLinkedList())) {
                //other > worker и other принадлежит текущему юзеру => удаляем other
                if (other.getCreatorId() == user.getId() && other.compareTo(worker) > 0) {
                    workerDatabaseManager.removeWorker(user, worker);
                }
            }
            //удаляем в коллекции
            collectionManager.removeGreater(worker, user.getId());
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Удалить работников не получилось");
        }
    }
}
