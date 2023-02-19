package commands;

import managers.CollectionManager;

/**
 * ������� help
 */
public class Help extends Command {
    private Command[] commands;

    public Help(Command[] commands) {
        super("help", "������� ������ ���������� � ��������");
        this.commands = commands;
    }

    /**
     * ������� �������� ������
     */
    public void execute() {
        try {
            for (Command command : commands) {
                System.out.println(command);
            }
        } catch (NullPointerException e) {

        }
    }

}
