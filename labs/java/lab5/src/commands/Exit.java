package commands;

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
    public void execute() {
        System.exit(0);
    }
}
