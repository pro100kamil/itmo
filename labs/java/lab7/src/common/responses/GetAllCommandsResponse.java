package common.responses;

import common.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Ответ на запрос о получении всех команд
 */
public class GetAllCommandsResponse extends Response {
    private final AbstractCommand[] commands;

    public GetAllCommandsResponse(AbstractCommand[] commands) {
        this.commands = commands;
//        ArrayList<AbstractCommand> tmp = new ArrayList<>();
//        for ()
//        this.commands = Arrays.stream(commands)
//                .map(command -> new AbstractCommand(command.getName(), command.getDescription()))
//                .toArray(AbstractCommand[]::new);
    }

    public AbstractCommand[] getCommands() {
        return commands;
    }
}
