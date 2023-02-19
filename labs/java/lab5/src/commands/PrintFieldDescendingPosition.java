package commands;

import managers.CollectionManager;

/**
 * ������� printFieldDescendingPostion
 */
public class PrintFieldDescendingPosition extends Command {
    private CollectionManager collectionManager;

    public PrintFieldDescendingPosition(CollectionManager collectionManager) {
        super("print_field_descending_position", "������� �������� ���� position ���� ��������� � ������� ��������");
        this.collectionManager = collectionManager;
    }

    /**
     * ������� �������� ���� position ���� ��������� � ������� ��������
     */
    public void execute() {
        try {
            collectionManager.printFieldDescendingPosition();
        } catch (NullPointerException e) {

        }
    }

    ;
}
