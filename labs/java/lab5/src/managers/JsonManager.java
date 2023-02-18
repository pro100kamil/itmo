package managers;

import models.*;
import json_serializer.*;

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
	
	public static String getStrJsonFromWorker(Worker worker) {
		//int target = 5;
		//Cat target = new Cat("cat1", 2, 10); 
		//Worker target = new Worker("Kamil", new Coordinates(Integer.valueOf(1), Integer.valueOf(2)), Float.valueOf(2.5f), Position.MANAGER, Status.FIRED); 
		//Coordinates target = new Coordinates(Integer.valueOf(1), Integer.valueOf(2)); 
		//Gson gson = new Gson();
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(worker);
		return json;
		//System.out.println(json);
	}
		
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
	
	public static Worker getWorkerFromStrJson(String json) {
		return gson.fromJson(json, Worker.class);
	}	
	
	public static LinkedList<Worker> getLinkedListWorkerFromStrJson(String json) {
		try {
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