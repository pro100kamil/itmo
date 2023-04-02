package commands;


import exceptions.WrongCommandArgsException;
import managers.Console;
import managers.ValidateManager;
import managers.CollectionManager;

/**
 * Команда filter_by_salary salary.
 * Выводит работников с заданной зарплатой.
 */
public class FilterBySalary extends Command {
    private final CollectionManager collectionManager;

    public FilterBySalary(CollectionManager collectionManager, Console console) {
        super("filter_by_salary", "выводит работников с заданной зарплатой", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !(args[0].equals("null") || ValidateManager.isFloat(args[0]))) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            Float salary = null;  //зарплата может быть null
            if (ValidateManager.isFloat(args[0])) {
                salary = Float.valueOf(args[0]);
            }
            collectionManager.getFilterBySalary(salary).forEach(worker -> console.write(worker.toString()));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
