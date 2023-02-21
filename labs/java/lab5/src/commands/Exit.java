package commands;

import exceptions.WrongCommandArgsException;

/**
 * Команда exit
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "завершает программу");
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
            System.out.println(e);
        }
    }
}
