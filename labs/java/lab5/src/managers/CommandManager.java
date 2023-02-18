package managers;

import commands.*;
import exceptions.*;

import java.util.LinkedList;

/**
 * Класс для работы с командами
 */
public class CommandManager {
	private LinkedList<Command> history = new LinkedList<>();

	/** Возвращает историю команд 
	 * @return Command[] массив из истории команд
	 */
	public LinkedList<Command> getHistory() {
		return history;
		/*Command[] res = new Command[history.size()];
		for (int i = 0; i < history.size(); i++) {
			res[i] = history.get(i);
		}
		return res;*/
	}	
	
	/** Возвращает все существующие команды
	 * @return Command[] массив из всех команд
	 */
	public Command[] getAllComands() {
		Command[] res = {new Help(null), new Info(), new Show(), new Add(null),
		new Update(null, null), new Remove(null), new Clear(), new Save(),
		new ExecuteScript(null, null), new Exit(), new Head(), new RemoveGreater(null),
		new History(null), new FilterBySalary(null), new PrintDescending(), new PrintFieldDescendingPosition()};
		return res;
	}
	
	
	
	/**
	 * Возвращает экземляр конкретной команды
	 * @param command - строка с командой 
	 * @return Command введённая корректная команда
	 */
	public Command getCommand(String command) throws NoSuchCommandException, WrongCommandArgsException {
		String[] subsCommand = command.split("\\s+");
		Command res = null;
		/*if (command.equals("help")) {
			res = new Help(getAllComands());
		}
		else if (command.equals("info")) {
			res = new Info();
		}
		else if (command.equals("show")) {
			res = new Show();
		}
		else if (command.equals("add")) {
			res = new Add(getWorker());
		}
		else if (command.equals("clear")) {
			res = new Clear();
		}
		else if (command.equals("head")) {
			res = new Head();
		}
		else if (command.equals("exit")) {
			res = new Exit();
		}
		else if (command.equals("remove_greater")) {
			res = new RemoveGreater(getWorker());
		}
		else if (command.equals("history")) {
			res = new History(getHistory());
		}
		else if (subsCommand.length == 2 && subsCommand[0].equals("update")) {
			if (!ValidateManager.isInteger(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new Update(Integer.valueOf(subsCommand[1]), getWorker());
		}
		else if (subsCommand.length == 2 && subsCommand[0].equals("remove_by_id")) {
			if (!ValidateManager.isInteger(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new Remove(Integer.valueOf(subsCommand[1]));
		}
		else if (subsCommand.length == 2 && subsCommand[0].equals("execute_script")) {
			if (!ValidateManager.isFile(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new ExecuteScript(subsCommand[1]);
		}
		else if (subsCommand.length == 2 && subsCommand[0].equals("filter_by_salary")) {
			if (ValidateManager.isFloat(subsCommand[1])) {
				res =  new FilterBySalary(Float.valueOf(subsCommand[1]));
			}
			else if (subsCommand[1] == null) {
				res = new FilterBySalary(null);
			}
			else {
				throw new WrongCommandArgsException();
			}
		}*/
		if (res == null) {
			throw new NoSuchCommandException();
		}
		history.add(res);
		return res;
	}
}