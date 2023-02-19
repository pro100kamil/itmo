package commands;

import managers.CollectionManager;

/**
 * ������� print_descending
 */
public class PrintDescending extends Command {
    private CollectionManager collectionManager;

    public PrintDescending(CollectionManager collectionManager) {
        super("print_descending", "������� �������� ��������� � ������� ��������");
        this.collectionManager = collectionManager;
    }

    /**
     * ������� �������� ��������� � ������� �������� (��������� ����������� �� ������� ��������)
     */
    public void execute() {
        try {
            collectionManager.printDescending();
        } catch (NullPointerException e) {

        }
    }

}
