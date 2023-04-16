package common.requests;

import common.commands.AbstractCommand;

public class ValidationRequest extends Request{
    private final AbstractCommand command;

    public ValidationRequest(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
