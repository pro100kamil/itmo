package common.network.requests;

import common.commands.AbstractCommand;

/**
 * Запрос на валидацию аргументов команды
 */
public class ValidationRequest extends Request{
    private final AbstractCommand command;

    public ValidationRequest(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
