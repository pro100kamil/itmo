package common.commands;

import common.models.Worker;

/**
 * Класс команды, которая принимает работника (добавить работника, удалить больше конкретного работника и обновить).
 * Всегда вначале проверяются аргументы, потом задаётся работник, а только потом команда выполняется.
 * А в обычных командах проверка аргументов происходит прямо при выполнении.
 */
public abstract class CommandWithWorker extends Command {

    protected CommandWithWorker(String name, String description) {
        super(name, description);
    }

    public abstract void setWorker(Worker worker);
}
