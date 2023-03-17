package managers;

import models.*;
import jsonAdapters.*;

import java.util.ArrayList;
import java.util.LinkedList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Класс для работы с json-файлами. Запись в них, считывание из них.
 */
public class JsonManager {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).registerTypeAdapter(
            LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
    private static Console console = new ConsoleManager();

    private static LinkedList<Worker> getLinkedList(Worker[] workers) {
        LinkedList<Worker> ll = new LinkedList<>();
        for (Worker worker : workers) {
            ll.add(worker);
        }
        return ll;
    }

    /**
     * Получает json-строку из связанного списка работников
     */
    public static String getStrJsonFromLinkedListWorker(LinkedList<Worker> workers) {
        try {
            String json = gson.toJson(workers);
            return json;
        } catch (Exception e) {
            console.write(e.toString());
            return "ошибка парсинга";
        }
    }

    /**
     * Получает из связанный список работников из json-строки
     */
    public static LinkedList<Worker> getLinkedListWorkerFromStrJson(String json) {
        try {
//            ArrayList<Worker> workers = new ArrayList<>();
//            if (!json.isEmpty()) { //есть возможность начать с чистого файла
//                workers = gson.fromJson(json, workers.getClass());
//            }
//            return new LinkedList(workers);
            Worker[] workers = new Worker[0];
            if (!json.isEmpty()) { //есть возможность начать с чистого файла
                workers = gson.fromJson(json, Worker[].class);
            }
            LinkedList<Worker> ll = new LinkedList<>();
            for (Worker worker : workers) {
                ll.add(worker);
            }
            return ll;
        } catch (Exception e) {
            console.write("Json-файл повреждён, данные из него не были взяты.");
            return new LinkedList<>();
        }
    }

    /**
     * Получает json-строку из связанного списка работников
     */
    public static String getStrJsonFromStepCollection(TreeMap<String, LinkedList<Worker>> stepCollection) {
        try {
            String json = gson.toJson(stepCollection);
            return json;
        } catch (Exception e) {
            console.write(e.toString());
            return "ошибка парсинга";
        }
    }

    /**
     * Получает из связанный список работников из json-строки
     */
    public static TreeMap<String, LinkedList<Worker>> getStepCollectionFromStrJson(String json, LinkedList<Worker> ll) {
        try {
            TreeMap<String, LinkedList<Worker>> stepCollection = new TreeMap<>();
            if (json.isEmpty()) {  //есть возможность начать с чистого файла
                stepCollection.put("0", ll);
            }
            else {
                TreeMap<String, ArrayList<Worker>> tmpStepCollection = new TreeMap<>();
                tmpStepCollection = gson.fromJson(json, tmpStepCollection.getClass());
                for (String k : tmpStepCollection.keySet()) {
                    stepCollection.put(k, tmpStepCollection.get(k).copy());
                }
                stepCollection.put(stepCollection.lastKey(), ll);
            }
            return stepCollection;
        } catch (Exception e) {
            console.write(e.toString());
            console.write("Json-файл для состояний коллекции повреждён, данные из него не были взяты.");
            return new TreeMap<String, LinkedList<Worker>>();
        }
    }
}