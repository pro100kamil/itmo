package common.network.requests;

import common.commands.AbstractCommand;

/**
 * Запрос на выполнение команды
 */
public class CommandRequest extends Request {
    private final AbstractCommand command;
    public CommandRequest(AbstractCommand abstractCommand) {
        this.command = abstractCommand;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
