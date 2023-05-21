package common.network.requests;

import common.commands.CommandDescription;

/**
 * Запрос на выполнение команды
 */
public class CommandRequest extends Request {
    private final CommandDescription command;
    public CommandRequest(CommandDescription abstractCommand) {
        this.command = abstractCommand;
    }

    public CommandDescription getCommand() {
        return command;
    }
}
