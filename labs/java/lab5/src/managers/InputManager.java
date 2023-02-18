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
	private String dataFileName;  //файл, из которого мы берём коллекцию и в который сохраняем
	
	public InputManager(Console console, CollectionManager collectionManager, String dataFileName) {
		this.console = console;
		this.collectionManager = collectionManager;
		this.dataFileName = dataFileName;
	}
	
	/**
	 * Получает целое число типа Integer (не null) из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @return Integer ввёденное целое число
	 */
	public Integer getInteger(String text) throws EndInputException {
		String tmp = ""; //временное хранение ввода
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (ValidateManager.isInteger(tmp)) {
				return Integer.parseInt(tmp);
			}
			console.write(text);
		}
		throw new EndInputException();
	}
		
	/**
	 * Получает вещественное число типа Float из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @param notNull true - число не может быть null, false - может
	 * @param positive true - число только положительное, false - любое
	 * @return Float ввёденное целое число (может быть null)
	 */
	public Float getFloat(String text, boolean notNull, boolean positive) throws EndInputException {
		String tmp = ""; //временное хранение ввода
		Float x;
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("") && !notNull) {
				return null;
			}
			if (ValidateManager.isFloat(tmp)) {
				x = Float.parseFloat(tmp);
				if (x > 0 || (x <= 0 && !positive)) {
					return x;
				}
			}
			console.write(text);
		}
		throw new EndInputException();
	}

	/**
	 * Получает позицию работника из стандартного ввода
	 * @return Position позиция работника (может быть null)
	 */
	public Position getPosition() throws EndInputException {
		String text = "Введите позицию работника или пустую строку для null. Варианты: ";
		for (Position el : Position.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //временное хранение ввода
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("")) {
				return null;
			}
			try {
				return Position.valueOf(tmp);
			}
			catch (IllegalArgumentException e) {
				console.write(text);
			}
		}
		throw new EndInputException();
	}

	/**
	 * Получает статус работника из стандартного ввода
	 * @return Status статус работника (может быть null)
	 */
	public Status getStatus() throws EndInputException {
		String text = "Введите статус работника или пустую строку для null. Варианты: ";
		for (Status el : Status.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //временное хранение ввода
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("")) {
				return null;
			}
			try {
				return Status.valueOf(tmp);
			}
			catch (IllegalArgumentException e) {
				console.write(text);
			}
		}
		throw new EndInputException();
	}

	/**
	 * Получает дату типа LocalDate (может быть null) из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @return LocalDate ввёденная дата (может быть null)
	 */
	public LocalDate getDate(String text) throws EndInputException {		
		String tmp = ""; //временное хранение ввода
		console.write(text);
		while (console.hasNext()) {
			tmp = console.getNextStr();
			if (tmp.equals("")) {
				return null;
			}
			try {
				return LocalDate.parse(tmp);
			}
			catch (DateTimeParseException e) {
				console.write(text);
			}
		}
		throw new EndInputException();
	}

	/**
	 * Получает строку состоящую не только из пробельных символов
	 * @param text текст, который подсказывает что и как вводить
	 * @param minLength минимальная допустимая длина для вводимой строки
	 * @param notNull true - число не может быть null, false - может
	 * @return Float ввёденное целое число (может быть null)
	 */
	public String getNotBlankString(String text, int minLength, boolean notNull) throws EndInputException {		
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
		throw new EndInputException();
	}

	/**
	 * Получает данные из стандартного ввода о работнике и возвращает работника 
	 * @return Worker работник с введёнными полями
	 */
	public Worker getWorker() {
		try {
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
		catch (EndInputException e) {
			return null;
		}
	}

	/**
	 * Возвращает экземляр конкретной команды
	 * @param command - строка с командой 
	 * @return Command введённая корректная команда
	 */
	public Command getCommand(String command) throws NoSuchCommandException, WrongCommandArgsException {
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
			Command[] allComands = {new Help(null), new Info(null), new Show(null), new Add(null, null),
new Update(null, null, null), new Remove(null, null), new Clear(null), new Save(null, null),
new ExecuteScript(null, null, null), new Exit(), new Head(null), new RemoveGreater(null, null),
new History(null), new FilterBySalary(null, null), new PrintDescending(null), new PrintFieldDescendingPosition(null)};
			res = new Help(allComands);
		}
		else if (subsCommand[0].equals("info")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Info(collectionManager);
		}
		else if (subsCommand[0].equals("show")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Show(collectionManager);
		}
		else if (subsCommand[0].equals("add")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Add(getWorker(), collectionManager);
		}
		else if (subsCommand[0].equals("update")) {
			if (subsCommand.length != 2 || !ValidateManager.isInteger(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new Update(Integer.valueOf(subsCommand[1]), getWorker(), collectionManager);
		}
		else if (subsCommand[0].equals("remove_by_id")) {
			if (subsCommand.length != 2 || !ValidateManager.isInteger(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new Remove(Integer.valueOf(subsCommand[1]), collectionManager);
		}
		else if (subsCommand[0].equals("clear")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Clear(collectionManager);
		}
		else if (subsCommand[0].equals("save")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new Save(dataFileName, collectionManager);
		}
		else if (subsCommand[0].equals("execute_script")) {
			if (subsCommand.length != 2 || !ValidateManager.isFile(subsCommand[1])) {
				throw new WrongCommandArgsException();
			}
			res = new ExecuteScript(subsCommand[1], collectionManager, dataFileName);
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
			res = new Head(collectionManager);
		}
		else if (subsCommand[0].equals("remove_greater")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new RemoveGreater(getWorker(), collectionManager);
		}
		else if (subsCommand[0].equals("history")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new History(history);
		}
		else if (subsCommand[0].equals("filter_by_salary")) {
			if (subsCommand.length == 2 && ValidateManager.isFloat(subsCommand[1])) {
				res =  new FilterBySalary(Float.valueOf(subsCommand[1]), collectionManager);
			}
			else if (subsCommand.length == 2 && subsCommand[1] == null) {
				res = new FilterBySalary(null, collectionManager);
			}
			else {
				throw new WrongCommandArgsException();
			}
		}
		else if (subsCommand[0].equals("print_descending")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new PrintDescending(collectionManager);
		}
		else if (subsCommand[0].equals("print_field_descending_position")) {
			if (subsCommand.length != 1) {
				throw new WrongCommandArgsException();
			}
			res = new PrintFieldDescendingPosition(collectionManager);
		}
		if (res == null) {
			throw new NoSuchCommandException();
		}
		history.add(res);
		return res;
	}
	
	/**
	 * Запускает интерактивный режим
	 */		
	public void run() {
		Command command;
		while (console.hasNext()) {
			String text = "Введите команду (help - чтобы узнать команды):";
			console.write(text);
			console.write("--------------------------");
			try {
				command = getCommand(console.getNextStr());
				if (command != null)
					command.execute();
			}
			catch (NoSuchCommandException | WrongCommandArgsException e) {
				console.write(e.toString());
			}
			catch (Exception e) {
				
			}
			console.write("--------------------------");
			console.write("");
		}
	}
}