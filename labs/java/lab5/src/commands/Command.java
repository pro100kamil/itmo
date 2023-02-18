package commands;

import managers.CollectionManager;

/**
 * Абстрактный класс команды
 */
public abstract class Command {
	private String name, desription;
		
	protected Command(String name, String desription) {
		this.name = name;
		this.desription = desription;
	}
	
	public String getName() {
		return name;
	}

	public abstract void execute(CollectionManager collectionManager);
	
	@Override
	public String toString() {
		return name + ": " + desription;
	}
}
