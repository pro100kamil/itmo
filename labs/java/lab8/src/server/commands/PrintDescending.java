package server.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда print_descending.
 * Выводит элементы коллекции в порядке убывания (работники сортируются по размеру зарплаты).
 */
public class PrintDescending extends ServerCommand {

    public PrintDescending() {
        super("print_descending",
                "выводит элементы коллекции в порядке убывания", false, true);
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
            collectionManager.printDescending();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
