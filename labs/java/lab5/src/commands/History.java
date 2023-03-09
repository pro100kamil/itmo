package commands;

import exceptions.WrongCommandArgsException;
import managers.Console;

import java.util.LinkedList;

/**
 * Команда history
 */
public class History extends Command {
    private LinkedList<Command> history;

    public History(LinkedList<Command> history, Console console) {
        super("history", "выводит последние 11 команд", console);
        this.history = history;
    }

    /**
     * Выводит последние 11 команд (внизу самая последняя)
     *
     * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            for (int i = Math.max(0, history.size() - 11); i < history.size(); i++) {
                console.write(history.get(i).getName());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }

    }

}
