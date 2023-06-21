package client.commands;

import common.commands.AbstractCommand;
import common.consoles.Console;

/**
 * Абстрактный класс команды, которая выполняется на клиенте.
 * Например, exit, execute_script, help.
 */
public abstract class ClientCommand extends AbstractCommand {
    protected Console console;

    public ClientCommand(String name, String description, boolean withWorker, boolean onlyUsers) {
        super(name, description, withWorker, onlyUsers);
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @param args - аргументы команды
     */
    public abstract void execute(String[] args);
}
