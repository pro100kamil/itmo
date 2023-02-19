package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� filter_by_salary
 */
public class FilterBySalary extends Command {
    private Float salary;
    private CollectionManager collectionManager;

    public FilterBySalary(Float salary, CollectionManager collectionManager) {
        super("filter_by_salary", "������� ���������� � �������� ���������");
        this.salary = salary;
        this.collectionManager = collectionManager;
    }

    /**
     * ������� ���������� � �������� ���������
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
