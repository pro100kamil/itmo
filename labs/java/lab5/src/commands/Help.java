package commands;

import managers.CollectionManager;

/**
 * Команда help
 */
public class Help extends Command {
	private Command[] commands;

	/*public Help(Command ... commands) {
		super("help", "выводит полную информацию о командах");
		this.commands = commands;
	}*/

	public Help(Command[] commands) {
		super("help", "выводит полную информацию о командах");
		this.commands = commands;
	}
	
	/**
	 * Выводит описание команд
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute(CollectionManager collectionManager) {
		for (Command command : commands) {
			System.out.println(command);	
		}
	};
}
