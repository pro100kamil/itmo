package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� filter_by_salary
 */
public class FilterBySalary extends Command {
	private Float salary;

	public FilterBySalary(Float salary) {
		super("filter_by_salary", "������� ���������� � �������� ���������");
		this.salary = salary;
	}

	/**
	 * ������� ���������� � �������� ���������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		for (Worker worker : collectionManager.getFilterBySalary(salary)) {
			System.out.println(worker);
		}
	};
}
