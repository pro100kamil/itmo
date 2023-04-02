package commands;

import exceptions.NonExistentId;
import exceptions.WrongCommandArgsException;
import models.Worker;
import managers.Console;

/**
 * Класс команды, которая принимает работника (добавить работника, удалить больше конкретного работника и обновить).
 * Всегда вначале проверяются аргументы, потом задаётся работник, а только потом команда выполняется.
 * А в обычных командах проверка аргументов происходит прямо при выполнении.
 */
public abstract class CommandWithWorker extends Command {

    protected CommandWithWorker(String name, String description, Console console) {
        super(name, description, console);
    }

    public abstract void setWorker(Worker worker);
}
