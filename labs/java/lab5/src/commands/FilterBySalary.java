package commands;

import exceptions.WrongCommandArgsException;
import managers.Console;
import managers.ValidateManager;
import models.Worker;
import managers.CollectionManager;

/**
 * Команда filter_by_salary
 */
public class FilterBySalary extends Command {
    private CollectionManager collectionManager;

    public FilterBySalary(CollectionManager collectionManager, Console console) {
        super("filter_by_salary", "выводит работников с заданной зарплатой", console);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит работников с заданной зарплатой
     */
    public void execute(String[] args) {
        try {
            Float salary = null;
            if (args.length == 1 && ValidateManager.isFloat(args[0])) {
                salary = Float.valueOf(args[0]);
            }
            else if (args.length == 1 && args[0].equals("null")) {
                salary = null;
            }
            else {
                throw new WrongCommandArgsException();
            }
            for (Worker worker : collectionManager.getFilterBySalary(salary)) {
                console.write(worker.toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}
