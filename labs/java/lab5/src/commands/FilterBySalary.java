package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * Команда filter_by_salary
 */
public class FilterBySalary extends Command {
    private Float salary;
    private CollectionManager collectionManager;

    public FilterBySalary(Float salary, CollectionManager collectionManager) {
        super("filter_by_salary", "выводит работников с заданной зарплатой");
        this.salary = salary;
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит работников с заданной зарплатой
     */
    public void execute() {
        try {
            for (Worker worker : collectionManager.getFilterBySalary(salary)) {
                System.out.println(worker);
            }
        } catch (NullPointerException e) {

        }
    }
}
