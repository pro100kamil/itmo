package commands;

import exceptions.WrongCommandArgsException;
import managers.Console;

/**
 * Команда exit
 */
public class Exit extends Command {
    public Exit(Console console) {
        super("exit", "завершает программу", console);
    }

    /**
     * Завершает программу
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            System.exit(0);
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
