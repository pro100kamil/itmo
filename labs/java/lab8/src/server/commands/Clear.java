package server.commands;

import common.exceptions.WrongCommandArgsException;

import java.sql.SQLException;

/**
 * Команда clear.
 * Очищает коллекцию.
 */
public class Clear extends ServerCommand {
    public Clear() {
        super("clear", "очищает коллекцию", false, true);
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
            //очищаем в бд
            int count = workerDatabaseManager.clearWorkers(user);
            console.write("Удалено работников: " + count);
            //очищаем в коллекции
            collectionManager.clear(user.getId());
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        } catch (SQLException e) {
            console.write("Очистить коллекцию не получилось");
        }
    }
}
