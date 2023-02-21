package commands;

import models.Worker;

/**
 * Класс команды, которая принимает работника (добавить работника, удалить больше конкретного работника и тд)
 */
public abstract class CommandWithWorker extends Command {

    protected CommandWithWorker(String name, String description) {
        super(name, description);
    }

    public abstract void setWorker(Worker worker);
}
