package commands;

import java.util.LinkedList;

/**
 * ������� history
 */
public class History extends Command {
	private LinkedList<Command> history;
	
	public History(LinkedList<Command> history) { 
		super("history", "������� ��������� 11 ������");
		this.history = history;
	}
	
	/**
	 * ������� ��������� 11 ������ (����� ����� ���������)
	 * @param collectionManager �������� ���������, ���������� �� �������� ���������
	 */
	public void execute() {
		for (int i = Math.max(0, history.size() - 11); i < history.size(); i++) {
			System.out.println(history.get(i).getName());
		}
	};
}
