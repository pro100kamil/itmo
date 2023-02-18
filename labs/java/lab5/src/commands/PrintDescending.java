package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� print_descending
 */
public class PrintDescending extends Command {

	public PrintDescending() {
		super("print_descending", "������� �������� ��������� � ������� ��������");
	}

	/**
	 * ������� �������� ��������� � ������� ��������
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		collectionManager.printDescending();
	};
}
