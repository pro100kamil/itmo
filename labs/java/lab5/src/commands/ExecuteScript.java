package commands;

import managers.FileConsole;

import models.Worker;
import managers.*;

/**
 * ������� execute_script file_name
 */
public class ExecuteScript extends Command {
	private static int depth = 0, maxDepth = 5;
	private String fileName, dataFileName;
	private CollectionManager collectionManager;
	

	public ExecuteScript(String fileName, CollectionManager collectionManager, String dataFileName) {
		super("execute_script", "��������� ������� �� �����");
		this.fileName = fileName;
		this.collectionManager = collectionManager;
		this.dataFileName = dataFileName;
	}

	/**
	 * ��������� ������� �� �����
	 */
	public void execute() {
		if (depth >= maxDepth) {
			System.out.println("��������� ������� ��������!");
			return;
		}
		System.out.println("�������� ��������� ����� � ���������");
		depth++;
		Console fileConsole = new FileConsole(fileName);
		InputManager newInputManager = new InputManager(fileConsole, collectionManager, dataFileName);
		newInputManager.run();
		depth--;
	};
}
