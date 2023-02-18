package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * Команда filter_by_salary
 */
public class FilterBySalary extends Command {
	private Float salary;

	public FilterBySalary(Float salary) {
		super("filter_by_salary", "выводит работников с заданной зарплатой");
		this.salary = salary;
	}

	/**
	 * Выводит работников с заданной зарплатой
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		for (Worker worker : collectionManager.getFilterBySalary(salary)) {
			System.out.println(worker);
		}
	};
}
