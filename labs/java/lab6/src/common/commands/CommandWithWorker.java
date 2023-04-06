package common.commands;

import common.models.Worker;

/**
 * Класс команды, которая принимает работника (добавить работника, удалить больше конкретного работника и обновить).
 * Всегда вначале проверяются аргументы, потом задаётся работник, а только потом команда выполняется.
 * А в обычных командах проверка аргументов происходит прямо при выполнении.
 */
public abstract class CommandWithWorker extends Command {
    protected Worker worker;
    protected CommandWithWorker(String name, String description) {
        super(name, description);
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }
}
