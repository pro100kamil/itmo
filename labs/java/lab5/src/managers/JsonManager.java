package managers;

import com.google.gson.reflect.TypeToken;
import models.*;
import jsonAdapters.*;

import java.lang.reflect.Type;
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
            LinkedList<Worker> workers = new LinkedList<>();
            if (!json.isEmpty()) { //есть возможность начать с чистого файла
                Type collectionType = new TypeToken<LinkedList<Worker>>() {
                }.getType();
                workers = gson.fromJson(json, collectionType);
            }
            return workers;
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
    public static TreeMap<String, LinkedList<Worker>> getStepCollectionFromStrJson(String json) {
        try {
            TreeMap<String, LinkedList<Worker>> stepCollection = new TreeMap<>();
            if (!json.isEmpty()) {
                Type collectionType = new TypeToken<TreeMap<String, LinkedList<Worker>>>() {
                }.getType();
                stepCollection = gson.fromJson(json, collectionType);
            }
            return stepCollection;
        } catch (Exception e) {
            console.write(e.toString());
            console.write("Json-файл для состояний коллекции повреждён, данные из него не были взяты.");
            return new TreeMap<String, LinkedList<Worker>>();
        }
    }

}