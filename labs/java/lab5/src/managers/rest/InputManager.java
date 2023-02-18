package managers;

import models.*;
import commands.*;

import java.util.Scanner;

/**
 * ����� ��� ������ �� ����������� ������
 */
public class InputManager {
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * �������� ����� ����� ���� Integer �� ������������ �����
	 * @param text �����, ������� ������������ ��� � ��� �������
	 * @return Integer �������� ����� �����
	 */
	public Integer getInteger(String text) {
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������������ ����� ���� Float �� ������������ �����
	 * @return Float �������� ����� ����� (����� ���� null)
	 */
	public Float getSalary() {
		String text = "������� �������� ��������� (������������ �����, ����� � ������� ����� ���������� ������) ��� ������ ������ ��� null:";
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������� ��������� �� ������������ �����
	 * @return Position ������� ��������� (����� ���� null)
	 */
	public Position getPosition() {
		String text = "������� ������� ��������� ��� ������ ������ ��� null. ��������: ";
		for (Position el : Position.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������ ��������� �� ������������ �����
	 * @return Status ������ ��������� (����� ���� null)
	 */
	public Status getStatus() {
		String text = "������� ������ ��������� ��� ������ ������ ��� null. ��������: ";
		for (Status el : Status.values()) {
			text += el + " ";
		}
		
		String tmp = ""; //��������� �������� �����
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
	 * �������� ������ �� ������������ ����� � ��������� � ���������� ��������� 
	 * @return Worker �������� � ��������� ������
	 */
	public Worker getWorker() {
		String tmp = ""; //��������� �������� �����
		
		System.out.println("������� ��� ���������:");
		String name = sc.nextLine();
		
		System.out.println("������� ���������� ��������� (X, Y).");
		
		Integer x = getInteger("������� X (����� �����):");
		Integer y = getInteger("������� Y (����� �����):");
		
		Coordinates coordinates = new Coordinates(x, y);
		
		Float salary = getSalary();
		
		Position position = getPosition();
		Status status = getStatus();
	
		return new Worker(name, new Coordinates(Integer.valueOf(x), Integer.valueOf(y)), salary, position, status);
	}

	/**
	 * �������� ������� �� ������������ ����� � ��������� � ���������� ��������� 
	 * @return Command �������� ���������� �������
	 */
	//public String getStrCommand() {
	public Command getCommand() {
		String text = "������� ������� (help - ����� ������ �������):";
		String tmp = ""; //��������� �������� �����
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