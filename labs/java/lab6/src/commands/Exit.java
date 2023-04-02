package commands;

import exceptions.WrongCommandArgsException;
import managers.Console;

/**
 * Команда exit.
 * Завершает программу.
 */
public class Exit extends Command {
    public Exit(Console console) {
        super("exit", "завершает программу", console);
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
            System.exit(0);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
