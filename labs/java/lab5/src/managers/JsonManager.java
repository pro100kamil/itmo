package managers;

import models.*;
import json_adapters.*;

import java.util.LinkedList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ����� ��� ������ � json-�������. ������ � ���, ���������� �� ���.
 */
public class JsonManager {
	private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).registerTypeAdapter(
LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
	
	/**
     * �������� json-������ �� ���������� ������ ����������
     */
	public static String getStrJsonFromLinkedListWorker(LinkedList<Worker> workers) {
		try {
			String json = gson.toJson(workers);
			return json;
		}
		catch (Exception e) {
			System.out.println(e);
			return "������ ��������";
		}
	}
	
	/**
     * �������� �� ��������� ������ ���������� �� json-������
     */
	public static LinkedList<Worker> getLinkedListWorkerFromStrJson(String json) {
		try {
			if (json.isEmpty()) { //���� ����������� ������ � ������� �����
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
			System.out.println("Json-���� ��������, ������ �� ���� �� ���� �����.");
			return new LinkedList<>();
		}
	}	
}