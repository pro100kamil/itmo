package commands;

import managers.FileConsole;

import models.Worker;
import managers.*;

/**
 * Команда execute_script file_name
 */
public class ExecuteScript extends Command {
	private String fileName;
	private static int depth = 0, maxDepth = 3;
	private CommandManager commandManager;

	public ExecuteScript(String fileName, CommandManager commandManager) {
		super("execute_script", "выполняет команды из файла");
		this.fileName = fileName;
		this.commandManager = commandManager;
	}

	/**
	 * Выполняет команды из файла
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		if (depth >= maxDepth) {
			System.out.println("Превышена глубина рекурсии!");
			return;
		}
		System.out.println("Начинаем обработку файла с командами");
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
