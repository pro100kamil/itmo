package server.managers;

import common.managers.FileManager;
import common.models.Worker;
import server.Configuration;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс, который работает с разными состояниями коллекции.
 */
public class CollectionHistory {
    //файл, где хранится текущее состояние коллекции
    private static String dataFileName = Configuration.getStartFileName();

    private static final String fileName = Configuration.getHistoryFileName();
    //файл, где хранятся данные о состояниях коллекции


    //private TreeMap<String, LinkedList<Worker>> stepCollection = new TreeMap<>();
    //номер команды (шага) в абсолютной нумерации: состояние списка на этот момент
    //нумерация с 0, 0 - начальное состояние коллекции

    /**
     * Берёт из файла состояний TreeMap<String, LinkedList<Worker>> stepCollection
     * Номер команды (шага) в абсолютной нумерации: состояние списка на этот момент
     * Нумерация с 0
     * 0 или номер последнего шага при прошлом запуске - начальное состояние коллекции
     */
    public TreeMap<String, LinkedList<Worker>> getStepCollection() {
        return JsonManager.getStepCollectionFromStrJson(common.managers.FileManager.getTextFromFile(CollectionHistory.getFileName()));
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setDataFileName(String newDataFileName) {
        dataFileName = newDataFileName;
    }

    /**
     * Берёт из файла текущее состояние коллекции (последний элемент в словаре состояний)
     */
    public LinkedList<Worker> getCurState() {
        TreeMap<String, LinkedList<Worker>> stepCollection = getStepCollection();
        return stepCollection.get(String.valueOf(stepCollection.size() - 1));
    }

    /**
     * Добавляет текущее состояние коллекции
     *
     * @param linkedList - текущее состояние
     */
    public void addStateCollection(LinkedList<Worker> linkedList) {
        TreeMap<String, LinkedList<Worker>> stepCollection = getStepCollection();
        stepCollection.put(String.valueOf(stepCollection.size()), linkedList);
        save(stepCollection);
    }

    /**
     * Устанавливает начальное состояние коллекции
     *
     * @param linkedList - начальное состояние коллекции
     */
    public void setStart(LinkedList<Worker> linkedList) {
        rollback(1);
        addStateCollection(linkedList);
    }

    /**
     * Отменяет действие последних n команд
     * Если n >= было команд всего, то отменятся все команды
     *
     * @param n кол-во команд, которые надо отменить
     */
    public void rollback(int n) {
        TreeMap<String, LinkedList<Worker>> stepCollection = getStepCollection();
        int total = stepCollection.size();
        //у нас имеются команды с номерами 0, ..., total - 1
        n = Math.max(0, Math.min(n, total - 1));
        // команду с номером 0 отменять не надо, потому что её не было, это просто изначальное состояние коллекции
        for (int i = total - n; i < total; i++) {
            stepCollection.remove(String.valueOf(i));
        }
        save(stepCollection);
    }

    /**
     * Сохраняет словарь текущих состояний в файл.
     * Ещё сохраняет текущее состояние в файл для текущего состояния.
     * Вызываем из других методов, чтобы в файле были самые актуальные состояния.
     *
     * @param stepCollection словарь текущих состояний
     */
    public void save(TreeMap<String, LinkedList<Worker>> stepCollection) {
        FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromStepCollection(stepCollection));
        if (getStepCollection().size() > 0) {
            FileManager.writeTextToFile(dataFileName, JsonManager.getStrJsonFromLinkedListWorker(getCurState()));
        }
    }
}
