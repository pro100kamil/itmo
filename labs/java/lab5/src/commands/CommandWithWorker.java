package commands;

import exceptions.NonExistentId;
import exceptions.WrongCommandArgsException;
import models.Worker;

/**
 * Класс команды, которая принимает работника (добавить работника, удалить больше конкретного работника и обновить)
 */
public abstract class CommandWithWorker extends Command {

    protected CommandWithWorker(String name, String description) {
        super(name, description);
    }

    public abstract void setWorker(Worker worker);

    /**
     * Проверяет аргументы на корректность. При неправильных аргументах бросает соответсвующее исключение.
     */
    public abstract void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId;
}
