package commands;

import managers.*;

/**
 * ������� save
 */
public class Save extends Command {
    private String fileName;
    private CollectionManager collectionManager;

    public Save(String fileName, CollectionManager collectionManager) {
        super("save", "��������� ��������� � ����");
        this.fileName = fileName;
        this.collectionManager = collectionManager;
    }

    /**
     * ��������� ��������� � ����
     */
    public void execute() {
        try {
            FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromLinkedListWorker(collectionManager.getLinkedList()));
        } catch (NullPointerException e) {

        }
    }

}
