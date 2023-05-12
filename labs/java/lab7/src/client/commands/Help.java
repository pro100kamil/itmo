package client.commands;

import common.commands.AbstractCommand;
import common.exceptions.WrongCommandArgsException;

import java.util.Arrays;

/**
 * Команда help.
 * Выводит описание команд.
 */
public class Help extends ClientCommand {
    private AbstractCommand[] commands;

    public Help() {
        super("help", "выводит полную информацию о командах", false, false);
    }

    public void setCommands(AbstractCommand[] commands) {
        this.commands = commands;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            Arrays.stream(commands).forEach(command -> console.write(command.toString()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
