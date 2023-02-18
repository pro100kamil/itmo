package commands;

import managers.FileConsole;

import models.Worker;
import managers.*;

/**
 * ������� execute_script file_name
 */
public class ExecuteScript extends Command {
	private String fileName;
	private static int depth = 0, maxDepth = 3;
	private CommandManager commandManager;

	public ExecuteScript(String fileName, CommandManager commandManager) {
		super("execute_script", "��������� ������� �� �����");
		this.fileName = fileName;
		this.commandManager = commandManager;
	}

	/**
	 * ��������� ������� �� �����
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute(CollectionManager collectionManager) {
		if (depth >= maxDepth) {
			System.out.println("��������� ������� ��������!");
			return;
		}
		System.out.println("�������� ��������� ����� � ���������");
		depth++;
		Console fileConsole = new FileConsole(fileName);
		InputManager newInputManager = new InputManager(fileConsole, collectionManager, commandManager);
		newInputManager.run();
		depth--;
		//String text = new FileManager.getFromFile(fileName);
		/*InputManager inputManager = new InputManager(console);
		while (true) {
			try {
				//command = commandManager.getCommand(console.getNextStr());
				//command = inputManager.getCommand();
				command = inputManager.getCommand(console.getNextStr(), commandManager);
				if (command != null)
					command.execute(collectionManager);
			}
			catch (Exception e) {
				console.write(e.toString());
			}
			console.write("--------------------------");
		}*/
	};
}
