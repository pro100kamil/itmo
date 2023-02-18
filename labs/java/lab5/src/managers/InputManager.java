package managers;

import models.*;
import commands.*;
import exceptions.*;

import java.util.LinkedList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Класс для работы с вводом
 */
public class InputManager {
	private LinkedList<Command> history = new LinkedList<>();
	
	private Console console;
	private CollectionManager collectionManager;
	private CommandManager commandManager;
	
	public InputManager(Console console, CollectionManager collectionManager, CommandManager commandManager) {
		this.console = console;
		this.collectionManager = collectionManager;
		this.commandManager = commandManager;
	}
	
		
	/**
	 * Получает целое число типа Integer (не null) из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @return Integer ввёденное целое число
	 */
	public Integer getInteger(String text) {
		String tmp = ""; //временное хранение ввода
		Integer x = 0;
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (ValidateManager.isInteger(tmp)) {
				x = Integer.parseInt(tmp);
				break;
			}
			console.write(text);
		}
		return x;
	}
		
	/**
	 * Получает вещественное число типа Float из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @param notNull true - число не может быть null, false - может
	 * @param positive true - число только положительное, false - любое
	 * @return Float ввёденное целое число (может быть null)
	 */
	public Float getFloat(String text, boolean notNull, boolean positive) {
		String tmp = ""; //временное хранение ввода
		Float x = 0f;
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("") && !notNull) {
				return null;
			}
			if (ValidateManager.isFloat(tmp)) {
				x = Float.parseFloat(tmp);
				if (x > 0 || (x <= 0 && !positive)) {
					break;
				}
			}
			console.write(text);
		}
		return x;
	}

	/**
	 * Получает позицию работника из стандартного ввода
	 * @return Position позиция работника (может быть null)
	 */
	public Position getPosition() {
		String text = "Введите позицию работника или пустую строку для null. Варианты: ";
		for (Position el : Position.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //временное хранение ввода
		Position x = null;
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("")) {
				return null;
			}
			try {
				x = Position.valueOf(tmp);
				break;
			}
			catch (IllegalArgumentException e) {
				console.write(text);
			}
		}
		return x;
	}

	/**
	 * Получает статус работника из стандартного ввода
	 * @return Status статус работника (может быть null)
	 */
	public Status getStatus() {
		String text = "Введите статус работника или пустую строку для null. Варианты: ";
		for (Status el : Status.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //временное хранение ввода
		Status x = null;
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("")) {
				return null;
			}
			try {
				x = Status.valueOf(tmp);
				break;
			}
			catch (IllegalArgumentException e) {
				console.write(text);
			}
		}
		return x;
	}

	/**
	 * Получает дату типа LocalDate (может быть null) из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @return LocalDate ввёденная дата (может быть null)
	 */
	public LocalDate getDate(String text) {		
		String tmp = ""; //временное хранение ввода
		console.write(text);
		LocalDate dt = LocalDate.now();
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("")) {
				return null;
			}
			try {
				dt = LocalDate.parse(tmp);
				break;
			}
			catch (DateTimeParseException e) {
				console.write(text);
			}
		}
		return dt;
	}

	/**
	 * Получает строку состоящую не только из пробельных символов
	 * @param text текст, который подсказывает что и как вводить
	 * @param minLength минимальная допустимая длина для вводимой строки
	 * @param notNull true - число не может быть null, false - может
	 * @return Float ввёденное целое число (может быть null)
	 */
	public String getNotBlankString(String text, int minLength, boolean notNull) {		
		String tmp = ""; //временное хранение ввода
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (!notNull && tmp.equals("")) {
				return null;
			}
			if (!tmp.isBlank() && tmp.length() >= minLength) {
				return tmp;
			}
			console.write(text);
		}
		return tmp;
	}

	/**
	 * Получает данные из стандартного ввода о работнике и возвращает работника 
	 * @return Worker работник с введёнными полями
	 */
	public Worker getWorker() {
		String tmp = ""; //временное хранение ввода
		
		String name = getNotBlankString("Введите имя работника:", 1, true);
		
		console.write("Введите координаты работника (X, Y).");
		
		Integer x = getInteger("Введите X (целое число):");
		Integer y = getInteger("Введите Y (целое число):");
		
		Coordinates coordinates = new Coordinates(x, y);
		
		Float salary = getFloat("Введите зарплату работника (вещественное число, целую и дробную часть разделяйте точкой) или пустую строку для null:", false, true);
		
		Position position = getPosition();
		Status status = getStatus();
		
		LocalDate birthday = getDate("Введите день рождения в формате 'yyyy-mm-dd' или пустую строку для null:");
		
		float height = getFloat("Введите рост работника (вещественнное число, целую и дробную часть разделяйте точкой):", true, true).floatValue();
		
		String passportID = getNotBlankString("Введите id пасспорта (более 6 символов) или пустую строку для null:", 7, false);
		
		Person person = new Person(birthday, height, passportID);
	
		return new Worker(name, new Coordinates(Integer.valueOf(x), Integer.valueOf(y)), salary, position, status, person);
	}

	/**
	 * Получает команду из стандартного ввода о работнике и возвращает работника 
	 * @return Command введённая корректная команда
	 */
	//public String getStrCommand() {
		
	/*public Command getCommand1() {
		String text = "Введите команду (help - чтобы узнать команды):";
		String tmp = ""; //временное хранение ввода
		console.write(text);
		boolean f = true;
		while (f) {
			tmp = console.getNextStr();
			String[] subsTmp = tmp.split("\\s+");
			if (tmp.equals("help")) {
				return new Help(null);
			}
			else if (tmp.equals("info")) {
				return new Info();
			}
			else if (tmp.equals("show")) {
				return new Show();
			}
			else if (tmp.equals("update id")) {
				return new Add(getWorker());
			}
			else if (tmp.equals("add")) {
				return new Add(getWorker());
			}
			else if (tmp.equals("clear")) {
				return new Clear();
			}
			else if (tmp.equals("head")) {
				return new Head();
			}
			else if (tmp.equals("exit")) {
				return new Exit();
			}
			else if (subsTmp.length == 2 && subsTmp[0].equals("update")) {
				//int id = 
				//return new Update();
			}
			else if (subsTmp.length == 2 && subsTmp[0].equals("remove_by_id")) {
				//int id = 
				//return new ();
			}
			else if (subsTmp.length == 2 && subsTmp[0].equals("filter_by_salary")) {
				if (ValidateManager.isFloat(subsTmp[1])) {
					return new FilterBySalary(Float.valueOf(subsTmp[1]));	
				}
				
			}
			console.write(text);
		}
		return new Help(null);
	}
*/
	/**
	
	 * Возвращает экземляр конкретной команды
	 * @param command - строка с командой 
	 * @return Command введённая корректная команда
	 */
	public Command getCommand(String command, CommandManager commandManager) throws NoSuchCommandException, WrongCommandArgsException {
		String[] subsCommand = command.split("\\s+");
		command = command.strip();
		Command res = null;
		if (subsCommand.length == 0) {
			throw new NoSuchCommandException();
		}
		if (subsCommand[0].equals("help")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Help(commandManager.getAllComands());
		}
		else if (subsCommand[0].equals("info")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Info();
		}
		else if (subsCommand[0].equals("show")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Show();
		}
		else if (subsCommand[0].equals("add")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Add(getWorker());
		}
		else if (subsCommand[0].equals("update")) {
			if (subsCommand.length != 2 || !ValidateManager.isInteger(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new Update(Integer.valueOf(subsCommand[1]), getWorker());
		}
		else if (subsCommand[0].equals("remove_by_id")) {
			if (subsCommand.length != 2 || !ValidateManager.isInteger(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new Remove(Integer.valueOf(subsCommand[1]));
		}
		else if (subsCommand[0].equals("clear")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Clear();
		}
		else if (subsCommand[0].equals("save")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Save();
		}
		else if (subsCommand[0].equals("execute_script")) {
			if (subsCommand.length != 2 || !ValidateManager.isFile(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new ExecuteScript(subsCommand[1], this.commandManager);
		}
		else if (subsCommand[0].equals("exit")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Exit();
		}
		else if (subsCommand[0].equals("head")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Head();
		}
		else if (subsCommand[0].equals("remove_greater")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new RemoveGreater(getWorker());
		}
		else if (subsCommand[0].equals("history")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new History(history);
		}
		else if (subsCommand[0].equals("filter_by_salary")) {
			if (subsCommand.length == 2 && ValidateManager.isFloat(subsCommand[1])) {
				res =  new FilterBySalary(Float.valueOf(subsCommand[1]));
			}
			else if (subsCommand.length == 2 && subsCommand[1] == null) {
				res = new FilterBySalary(null);
			}
			else {
				throw new WrongCommandArgsException();
			}
		}
		else if (subsCommand[0].equals("print_descending")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new PrintDescending();
		}
		else if (subsCommand[0].equals("print_field_descending_position")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new PrintFieldDescendingPosition();
		}
		if (res == null) {
			throw new NoSuchCommandException();
		}
		history.add(res);
		return res;
	}
	
		
	public void run() {
		Command command;
		while (console.hasNext()) {
			String text = "Введите команду (help - чтобы узнать команды):";
			console.write(text);
			try {
				command = getCommand(console.getNextStr(), commandManager);
				if (command != null)
					command.execute(collectionManager);
			}
			catch (Exception e) {
				console.write(e.toString());
			}
			console.write("--------------------------");
		}
	}
}