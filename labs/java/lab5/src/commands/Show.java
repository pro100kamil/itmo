package commands;

import managers.CollectionManager;

/**
 * ������� show
 */
public class Show extends Command {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "������� �������� ���������");
        this.collectionManager = collectionManager;
    }

    /**
     * ������� �������� ���������
     */
    public void execute() {
        try {
            collectionManager.printElements();
        } catch (NullPointerException e) {

        }
    }
}
