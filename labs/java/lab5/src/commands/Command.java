package commands;

import managers.CollectionManager;

/**
 * Абстрактный класс команды
 */
public abstract class Command {
    private String name, description;

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
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
