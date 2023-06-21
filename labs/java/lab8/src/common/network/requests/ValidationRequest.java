package common.network.requests;

import common.commands.AbstractCommand;
import common.commands.CommandDescription;

/**
 * Запрос на валидацию аргументов команды
 */
public class ValidationRequest extends Request{
    private final CommandDescription command;

    public ValidationRequest(CommandDescription command) {
        this.command = command;
    }

    public CommandDescription getCommand() {
        return command;
    }
}
