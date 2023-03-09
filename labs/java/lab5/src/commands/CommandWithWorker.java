package commands;

import exceptions.NonExistentId;
import exceptions.WrongCommandArgsException;
import models.Worker;
import managers.Console;

/**
 * Класс команды, которая принимает работника (добавить работника, удалить больше конкретного работника и обновить)
 */
public abstract class CommandWithWorker extends Command {

    protected CommandWithWorker(String name, String description, Console console) {
        super(name, description, console);
    }

    public abstract void setWorker(Worker worker);

    /**
     * Проверяет аргументы на корректность. При неправильных аргументах бросает соответсвующее исключение.
     */
    public abstract void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId;
}
