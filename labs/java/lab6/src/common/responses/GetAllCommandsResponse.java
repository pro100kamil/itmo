package common.responses;

import common.commands.AbstractCommand;

/**
 * Ответ на запрос о получении всех команд
 */
public class GetAllCommandsResponse extends Response {
    AbstractCommand[] commands;

    public GetAllCommandsResponse(AbstractCommand[] commands) {
        this.commands = commands;
    }

    public AbstractCommand[] getCommands() {
        return commands;
    }
}
