package commands;

/**
 * ������� exit
 */
public class Exit extends Command {
	public Exit() { 
		super("exit", "��������� ���������");
	}
	
	/**
	 * ��������� ���������
	 */
	public void execute() {
		System.exit(0);
	};
}
