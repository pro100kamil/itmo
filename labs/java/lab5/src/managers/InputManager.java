package managers;

import models.*;
import commands.*;
import exceptions.*;

import java.util.LinkedList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * ����� ��� ������ � ������
 */
public class InputManager {
	private LinkedList<Command> history = new LinkedList<>();
	private Console console;
	private CollectionManager collectionManager;
	private String dataFileName;  //����, �� �������� �� ���� ��������� � � ������� ���������
	
	public InputManager(Console console, CollectionManager collectionManager, String dataFileName) {
		this.console = console;
		this.collectionManager = collectionManager;
		this.dataFileName = dataFileName;
	}
	
	/**
	 * �������� ����� ����� ���� Integer (�� null) �� ������������ �����
	 * @param text �����, ������� ������������ ��� � ��� �������
	 * @return Integer �������� ����� �����
	 */
	public Integer getInteger(String text) throws EndInputException {
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������������ ����� ���� Float �� ������������ �����
	 * @param text �����, ������� ������������ ��� � ��� �������
	 * @param notNull true - ����� �� ����� ���� null, false - �����
	 * @param positive true - ����� ������ �������������, false - �����
	 * @return Float �������� ����� ����� (����� ���� null)
	 */
	public Float getFloat(String text, boolean notNull, boolean positive) throws EndInputException {
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������� ��������� �� ������������ �����
	 * @return Position ������� ��������� (����� ���� null)
	 */
	public Position getPosition() throws EndInputException {
		String text = "������� ������� ��������� ��� ������ ������ ��� null. ��������: ";
		for (Position el : Position.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������ ��������� �� ������������ �����
	 * @return Status ������ ��������� (����� ���� null)
	 */
	public Status getStatus() throws EndInputException {
		String text = "������� ������ ��������� ��� ������ ������ ��� null. ��������: ";
		for (Status el : Status.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //��������� �������� �����
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
	 * �������� ���� ���� LocalDate (����� ���� null) �� ������������ �����
	 * @param text �����, ������� ������������ ��� � ��� �������
	 * @return LocalDate �������� ���� (����� ���� null)
	 */
	public LocalDate getDate(String text) throws EndInputException {		
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������ ��������� �� ������ �� ���������� ��������
	 * @param text �����, ������� ������������ ��� � ��� �������
	 * @param minLength ����������� ���������� ����� ��� �������� ������
	 * @param notNull true - ����� �� ����� ���� null, false - �����
	 * @return Float �������� ����� ����� (����� ���� null)
	 */
	public String getNotBlankString(String text, int minLength, boolean notNull) throws EndInputException {		
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������ �� ������������ ����� � ��������� � ���������� ��������� 
	 * @return Worker �������� � ��������� ������
	 */
	public Worker getWorker() {
		try {
			String tmp = ""; //��������� �������� �����
			
			String name = getNotBlankString("������� ��� ���������:", 1, true);
			
			console.write("������� ���������� ��������� (X, Y).");
			
			Integer x = getInteger("������� X (����� �����):");
			Integer y = getInteger("������� Y (����� �����):");
			
			Coordinates coordinates = new Coordinates(x, y);
			
			Float salary = getFloat("������� �������� ��������� (������������ �����, ����� � ������� ����� ���������� ������) ��� ������ ������ ��� null:", false, true);
			
			Position position = getPosition();
			Status status = getStatus();
			
			LocalDate birthday = getDate("������� ���� �������� � ������� 'yyyy-mm-dd' ��� ������ ������ ��� null:");
			
			float height = getFloat("������� ���� ��������� (������������� �����, ����� � ������� ����� ���������� ������):", true, true).floatValue();
			
			String passportID = getNotBlankString("������� id ��������� (����� 6 ��������) ��� ������ ������ ��� null:", 7, false);
			
			Person person = new Person(birthday, height, passportID);
		
			return new Worker(name, new Coordinates(Integer.valueOf(x), Integer.valueOf(y)), salary, position, status, person);
		}
		catch (EndInputException e) {
			return null;
		}
	}

	/**
	 * ���������� �������� ���������� �������
	 * @param command - ������ � �������� 
	 * @return Command �������� ���������� �������
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
	 * ��������� ������������� �����
	 */		
	public void run() {
		Command command;
		while (console.hasNext()) {
			String text = "������� ������� (help - ����� ������ �������):";
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