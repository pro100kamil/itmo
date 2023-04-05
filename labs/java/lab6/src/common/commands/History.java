package common.commands;

import common.exceptions.WrongCommandArgsException;

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
//            history.stream();
            //TODO сделать через стримы
            for (int i = Math.max(0, history.size() - 11); i < history.size(); i++) {
                console.write(history.get(i).getName());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
