package managers;

import models.*;
import json_adapters.*;

import java.util.LinkedList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Класс для работы с json-файлами. Запись в них, считывание из них.
 */
public class JsonManager {
	private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).registerTypeAdapter(
LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
	
	/**
     * Получает json-строку из связанного списка работников
     */
	public static String getStrJsonFromLinkedListWorker(LinkedList<Worker> workers) {
		try {
			String json = gson.toJson(workers);
			return json;
		}
		catch (Exception e) {
			System.out.println(e);
			return "ошибка парсинга";
		}
	}
	
	/**
     * Получает из связанный список работников из json-строки
     */
	public static LinkedList<Worker> getLinkedListWorkerFromStrJson(String json) {
		try {
			if (json.isEmpty()) { //есть возможность начать с чистого файла
				json = "[]";
			}
			Worker[] workers = gson.fromJson(json, Worker[].class);
			LinkedList<Worker> ll = new LinkedList<>();
			for (Worker worker : workers) {
				ll.add(worker);
			}
			return ll;
		}
		catch (Exception e) {
			System.out.println("Json-файл повреждён, данные из него не были взяты.");
			return new LinkedList<>();
		}
	}	
}