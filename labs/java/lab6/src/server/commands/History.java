package server.commands;

import common.exceptions.WrongCommandArgsException;
import server.commands.Command;

/**
 * Команда history.
 * Выводит последние 11 команд (внизу самая последняя).
 * Тут учитываются все команды (кроме команд внутри файла), даже если они были отменены и даже сама команда rollback.
 */
public class History extends Command {

    public History() {
        super("history", "выводит последние 11 команд");
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
            history.stream().skip(Math.max(0, history.size() - 11)).forEach(command -> console.write(command.getName()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
