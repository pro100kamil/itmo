package commands;

import exceptions.NotUniqueIdException;
import exceptions.WrongCommandArgsException;

/**
 * Команда help
 */
public class Help extends Command {
    private Command[] commands;

    public Help(Command[] commands) {
        super("help", "выводит полную информацию о командах");
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
                System.out.println(command);
            }
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }

    }

}
