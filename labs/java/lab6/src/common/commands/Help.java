package common.commands;

import common.exceptions.WrongCommandArgsException;

import java.util.Arrays;

/**
 * Команда help.
 * Выводит описание команд.
 */
public class Help extends Command {
    private final Command[] commands;

    public Help(Command[] commands) {
        super("help", "выводит полную информацию о командах");
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
            Arrays.stream(commands).forEach(worker -> console.write(worker.toString()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
