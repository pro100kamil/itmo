package server.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда exit.
 * Завершает программу.
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "завершает программу");
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
