package commands;

import managers.CollectionManager;

/**
 * јбстрактный класс команды
 */
public abstract class Command {
    private String name, desription;

    protected Command(String name, String desription) {
        this.name = name;
        this.desription = desription;
    }

    public String getName() {
        return name;
    }

    /**
     * ¬ыполняет команду
     */
    public abstract void execute();

    @Override
    public String toString() {
        return name + ": " + desription;
    }
}
