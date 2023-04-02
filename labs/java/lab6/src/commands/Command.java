package commands;

import exceptions.NonExistentId;
import exceptions.WrongCommandArgsException;
import managers.Console;

/**
 * Абстрактный класс команды
 */
public abstract class Command {
    private final String name;
    private final String description;
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
     * Проверяет аргументы на корректность. При неправильных аргументах бросает соответсвующее исключение.
     *
     * @param args - аргументы команды
     */
    public abstract void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId;

    /**
     * Выполняет команду
     *
     * @param args - аргументы команды
     */
    public abstract void execute(String[] args);

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
