package common.requests;

import common.commands.AbstractCommand;

/**
 * Запрос, содержащий абстрактную команду.
 * Вначале команда отправляется на валидацию.
 * Потом команда отправляется на выполнение.
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
