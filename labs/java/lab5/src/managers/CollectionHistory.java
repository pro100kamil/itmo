package managers;

import models.Worker;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс, который работает с коллекциями, которые были на разных шагах
 */
public class CollectionHistory {
    private static String fileName = "_.json";   //файл, где хранятся данные о состояниях коллекции
    private TreeMap<Integer, LinkedList<Worker>> stepCollection = new TreeMap<>();
    //номер команды (шага) в абсолютной нумерации: состояние списка на этот момент
    //нумерация с 0, 0 - пустая коллекция

    public CollectionHistory(TreeMap<Integer, LinkedList<Worker>> stepCollection) {
        this.stepCollection = stepCollection;
    }

    public static String getFileName() {
        return fileName;
    }

    public LinkedList<Worker> getCurState(){
        return stepCollection.get(stepCollection.lastKey());
    }

    /**
     * Добавляет текущее состояние коллекции
     * @param linkedList - текущее состояние
     */
    public void addStateCollection(LinkedList<Worker> linkedList) {
        stepCollection.put(stepCollection.size(), new LinkedList<Worker>(linkedList));
        save();
    }

    /**
     * Отменяет действие последних n комманд
     * @param n кол-во команд, которые надо отменить
     */
    public void rollback(int n) {
        int total = stepCollection.size();
        n = Math.min(n, total - 1);
        for (int i = total - n; i < total; i++) {
            stepCollection.remove(i);
        }
        save();
    }

    /**
     * Сохраняет словарь текущих состояний в файл.
     * Вызываем из других методов, чтобы в файле были самые актуальные состояния.
     */
    public void save() {
        FileManager.writeTextToFile(fileName, JsonManager.getStrJsonFromStepCollection(stepCollection));
    }
}
