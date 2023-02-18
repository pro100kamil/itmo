package managers;

import models.*;
import commands.*;

import java.util.Scanner;

/**
 * Класс для работы со стандартным вводом
 */
public class InputManager {
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Получает целое число типа Integer из стандартного ввода
	 * @param text текст, который подсказывает что и как вводить
	 * @return Integer ввёденное целое число
	 */
	public Integer getInteger(String text) {
		String tmp = ""; //временное хранение ввода
		Integer x = 0;
		System.out.println(text);
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			try {
				x = Integer.parseInt(tmp);
				break;
			}
			catch (NumberFormatException e) {
				System.out.println(text);
			}
		}
		return x;
	}
		
	/**
	 * Получает вещественное число типа Float из стандартного ввода
	 * @return Float ввёденное целое число (может быть null)
	 */
	public Float getSalary() {
		String text = "Введите зарплату работника (вещественное число, целую и дробную часть разделяйте точкой) или пустую строку для null:";
		String tmp = ""; //временное хранение ввода
		Float x = 0f;
		System.out.println(text);
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			if (tmp.equals("")) {
				return null;
			}
			try {
				x = Float.parseFloat(tmp);
				break;
			}
			catch (NumberFormatException e) {
				System.out.println(text);
			}
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
		System.out.println(text);
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			if (tmp.equals("")) {
				return null;
			}
			try {
				x = Position.valueOf(tmp);
				break;
			}
			catch (IllegalArgumentException e) {
				System.out.println(text);
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
		System.out.println(text);
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			if (tmp.equals("")) {
				return null;
			}
			try {
				x = Status.valueOf(tmp);
				break;
			}
			catch (IllegalArgumentException e) {
				System.out.println(text);
			}
		}
		return x;
	}

	/**
	 * Получает данные из стандартного ввода о работнике и возвращает работника 
	 * @return Worker работник с введёнными полями
	 */
	public Worker getWorker() {
		String tmp = ""; //временное хранение ввода
		
		System.out.println("Введите имя работника:");
		String name = sc.nextLine();
		
		System.out.println("Введите координаты работника (X, Y).");
		
		Integer x = getInteger("Введите X (целое число):");
		Integer y = getInteger("Введите Y (целое число):");
		
		Coordinates coordinates = new Coordinates(x, y);
		
		Float salary = getSalary();
		
		Position position = getPosition();
		Status status = getStatus();
	
		return new Worker(name, new Coordinates(Integer.valueOf(x), Integer.valueOf(y)), salary, position, status);
	}

	/**
	 * Получает команду из стандартного ввода о работнике и возвращает работника 
	 * @return Command введённая корректная команда
	 */
	//public String getStrCommand() {
	public Command getCommand() {
		String text = "Введите команду (help - чтобы узнать команды):";
		String tmp = ""; //временное хранение ввода
		System.out.println(text);
		while (sc.hasNextLine()) {
			tmp = sc.nextLine();
			/*String[] subsTmp = tmp.split("\\s+");
			if (tmp.equals("help")) {
				return new Help(new Help(), new Info(), new Add(null), new Show(), new Clear(), new Head(), new Exit(), new FilterBySalary(null));
			}
			else if (tmp.equals("info")) {
				return new Info();
			}
			else if (tmp.equals("show")) {
				return new Show();
			}
			else if (tmp.equals("update id")) {
				//return new Add(getWorker());
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
				return new FilterBySalary(Float.valueOf(subsTmp[1]));
			}
			System.out.println(text);*/
		}
		return null;
	}
}