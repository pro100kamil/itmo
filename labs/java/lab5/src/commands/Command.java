package commands;

import managers.CollectionManager;
import managers.Console;

/**
 * Абстрактный класс команды
 */
public abstract class Command {
    private String name, description;
    protected Console console;

    protected Command(String name, String description, Console console) {
        this.name = name;
        this.description = description;
        this.console = console;
    }

    public String getName() {
        return name;
    }

    /**
     * Выполняет команду
     */
    public abstract void execute(String[] args);

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
