package commands;

import java.util.LinkedList;

/**
 * Команда history
 */
public class History extends Command {
	private LinkedList<Command> history;
	
	public History(LinkedList<Command> history) { 
		super("history", "выводит последние 11 команд");
		this.history = history;
	}
	
	/**
	 * Выводит последние 11 команд (внизу самая последняя)
	 * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
	 */
	public void execute() {
		for (int i = Math.max(0, history.size() - 11); i < history.size(); i++) {
			System.out.println(history.get(i).getName());
		}
	};
}
