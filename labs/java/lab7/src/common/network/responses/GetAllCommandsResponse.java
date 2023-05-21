package common.network.responses;

import common.commands.CommandDescription;

/**
 * Ответ на запрос о получении всех команд
 */
public class GetAllCommandsResponse extends Response {
    private final CommandDescription[] commands;

    public GetAllCommandsResponse(CommandDescription[] commands) {
        this.commands = commands;
    }

    public CommandDescription[] getCommands() {
        return commands;
    }
}
