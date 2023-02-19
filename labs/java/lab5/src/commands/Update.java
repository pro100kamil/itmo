package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * ������� update
 */
public class Update extends Command {
    private Integer id;
    private Worker elem;
    private CollectionManager collectionManager;

    public Update(Integer id, Worker elem, CollectionManager collectionManager) {
        super("update", "��������� ��������� �� id �� ������ ��������� ���������");
        this.id = id;
        this.elem = elem;
        this.collectionManager = collectionManager;
    }

    /**
     * ��������� ��������� �� id �� ������ �������� ���������
     */
    public void execute() {
        try {
            collectionManager.update(id, elem);
        } catch (NullPointerException e) {

        }
    }

}
