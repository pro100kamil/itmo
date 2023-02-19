package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� remove_greater
 */
public class RemoveGreater extends Command {
    private Worker elem;
    private CollectionManager collectionManager;

    public RemoveGreater(Worker elem, CollectionManager collectionManager) {
        super("remove_greater", "������� ����������, ������� ������ ���������");
        this.elem = elem;
        this.collectionManager = collectionManager;
    }

    /**
     * ������� ����������, ������� ������ ���������
     */
    public void execute() {
        try {
            collectionManager.removeGreater(elem);
        } catch (NullPointerException e) {

        }
    }

}
