package managers;

import models.Worker;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс, который работает с коллекциями, которые были на разных шагах.
 */
public class CollectionHistory {
    private static final String fileName = "_.json";   //файл, где хранятся данные о состояниях коллекции
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
        return JsonManager.getStepCollectionFromStrJson(FileManager.getTextFromFile(CollectionHistory.getFileName()));
    }

    public static String getFileName() {
        return fileName;
    }

    /**
     * Берёт из файла текущее состояние коллекции (последний элемент в словаре состояний)
     */
    public LinkedList<Worker> getCurState() {
        TreeMap<String, LinkedList<Worker>> stepCollection = getStepCollection();
        return stepCollection.get(stepCollection.lastKey());
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
     * Вызываем из других методов, чтобы в файле были самые актуальные состояния.
     *
     * @param stepCollection словарь текущих состояний
     */
    public void save(TreeMap<String, LinkedList<Worker>> stepCollection) {
        FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromStepCollection(stepCollection));
    }
}
