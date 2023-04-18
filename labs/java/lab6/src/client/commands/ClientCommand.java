package client.commands;

import common.commands.AbstractCommand;
import common.consoles.Console;

/**
 * Класс команды, которая выполняется на клиенте
 * Например, exit, execute_script, help.
 */
public abstract class ClientCommand extends AbstractCommand {
    protected Console console;

    protected ClientCommand(String name, String description) {
        super(name, description);
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
