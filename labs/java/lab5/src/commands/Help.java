package commands;

import managers.CollectionManager;

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
    public void execute() {
        try {
            for (Command command : commands) {
                System.out.println(command);
            }
        } catch (NullPointerException e) {

        }
    }

}
