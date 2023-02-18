package managers;

import commands.*;
import exceptions.*;

/**
 * ????? ??? ?????? ? ?????????????
 */
public class UserManager {
	private Console console;
	private CollectionManager collectionManager;
	private CommandManager commandManager;
	
	public UserManager(Console console, CollectionManager collectionManager, CommandManager commandManager) {
		this.console = console;
		this.collectionManager = collectionManager;
		this.commandManager = commandManager;
	}
	
	public void run() {
		InputManager inputManager = new InputManager(console, collectionManager, commandManager);
		inputManager.run();
		/*while (true) {
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
	}
}