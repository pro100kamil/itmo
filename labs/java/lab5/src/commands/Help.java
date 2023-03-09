package commands;

import exceptions.NotUniqueIdException;
import exceptions.WrongCommandArgsException;
import managers.Console;

/**
 * Команда help
 */
public class Help extends Command {
    private Command[] commands;

    public Help(Command[] commands, Console console) {
        super("help", "выводит полную информацию о командах", console);
        this.commands = commands;
    }

    /**
     * Выводит описание команд
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            for (Command command : commands) {
                console.write(command.toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
