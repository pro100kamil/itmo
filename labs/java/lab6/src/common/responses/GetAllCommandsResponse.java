package common.responses;

import common.commands.AbstractCommand;


public class GetAllCommandsResponse extends Response {
    AbstractCommand[] commands;

    public GetAllCommandsResponse(AbstractCommand[] commands) {
        this.commands = commands;
    }

    public AbstractCommand[] getCommands() {
        return commands;
    }
}
