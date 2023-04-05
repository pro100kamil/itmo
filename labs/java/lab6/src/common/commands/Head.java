package common.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда head.
 * Выводит первый элемент коллекции.
 */
public class Head extends Command {

    public Head() {
        super("head", "выводит первый элемент коллекции");
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
            if (collectionManager.isEmpty()) {
                console.write("Коллекция пустая, поэтому нет первого элемента!");
            } else {
                console.write(collectionManager.getHead().toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
