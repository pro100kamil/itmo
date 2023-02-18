package managers;

import models.Worker;
import exceptions.NotUniqueIdException;

import java.util.LinkedList;
import java.util.Collections;
import java.util.TreeMap;
import java.time.LocalDateTime;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
	private LinkedList<Worker> linkedList;
	private TreeMap<Integer, Worker> idWorkerFromCollection = new TreeMap<>();
	private LocalDateTime creationDate;

	public CollectionManager() {
		creationDate = LocalDateTime.now();
		linkedList = new LinkedList<>();
	}
	
	public CollectionManager(LinkedList<Worker> workers) {
		//если несколько одинаковых id, оставляем первый встречный
		//оставляем только корректных работников
		this();
		Integer maxId = 0;
		for (Worker worker : workers) {
			if (worker != null && worker.validate()) {
				try {
					add(worker);
					maxId = Math.max(maxId, worker.getId());
				}
				catch (NotUniqueIdException e) {
					
				}
			}
		}
		Worker.moveNextId(maxId + 1);
	}
	
	/**
	 * Проверяет, корректная ли у нас коллекция работников
	 * То есть все работники корректные и все id разные
	 */
	public boolean validate() {
		for (Worker worker : linkedList) {
			if (!worker.validate() || idWorkerFromCollection.get(worker.getId()) != worker) {
				return false;
			}
		}
		return true;
	}
	
	/**Пустая ли коллекция
	 * @return boolean true - коллекция пуста,false - в ней есть элементы
	 */
	public boolean isEmpty() {
		return linkedList.size() == 0;
	}

	/**Выводит информацию о коллекции
	 */
	public void printInfo() {
		System.out.println("Тип данных: " + linkedList.getClass().getName());
		System.out.println("Дата инициализации: " + creationDate);
		System.out.println("Количество элементов: " + linkedList.size());
	}
	
	/**Вывод всех элементов коллекции
	 */
	public void printElements() {
		if (linkedList.size() == 0) {
			System.out.println("Коллекция пустая");
			return;
		}
		System.out.println("Элементы коллекции: " + linkedList.size());
		int i = 0;
		for (Worker worker : linkedList) {
			System.out.print(worker);
			if (i != linkedList.size() - 1) {
				System.out.print(", ");
			}
			i += 1;
		}
		System.out.println(".");
	}
	
	/**
	 * Выводит элементы коллекции в порядке убывания
	 */
	public void printDescending() {
		LinkedList<Worker> copyList = (LinkedList<Worker>)linkedList.clone();
		Collections.sort(copyList);
		Collections.reverse(copyList);
		for (Worker worker : copyList) {
			System.out.println(worker);
		}
	}
	
	/**
	 * Выводит позиции работников, работники идут по убыванию
	 */
	public void printFieldDescendingPosition() {
		LinkedList<Worker> copyList = (LinkedList<Worker>)linkedList.clone();
		Collections.sort(copyList);
		Collections.reverse(copyList);
		for (Worker worker : copyList) {
			System.out.println(worker.getPosition());
		}
	}
	
	/**
	 * Добавляет работника в коллекцию
	 * @param worker работник, которого мы добавляем
	 */
	public void add(Worker worker) throws NotUniqueIdException {
		if (idWorkerFromCollection.containsKey(worker.getId())) { //если уже есть пользователь с таким id
			throw new NotUniqueIdException();
		}
		idWorkerFromCollection.put(worker.getId(), worker);
		linkedList.add(worker);
	}
	
	/**
	 * Обновляет работника по id на основе заданное работника
	 * @param id работника
	 * @param worker заданный работник
	 */
	public void update(int id, Worker worker) {
		if (!idWorkerFromCollection.containsKey(worker.getId())) { //если нет пользователя с таким id
			System.out.println("Нет пользователя с таким id!");
			return;
		}
		Worker w1 = idWorkerFromCollection.get(id);
		w1.update(worker);
	}
	
	/**
	 * Удаляем работника из коллекции
	 * @param id работника
	 */
	public void remove(int id) {
		if (!idWorkerFromCollection.containsKey(id)) { //если нет пользователя с таким id
			System.out.println("Нет пользователя с таким id!");
			return;
		}
		Worker worker = idWorkerFromCollection.get(id);
		linkedList.remove(worker);
	}
	
	/**
	 * Удаляет работников, которые больше (по зарплате) заданного
	 * @param worker заданный работник
	 */
	public void removeGreater(Worker worker) {
		for (Worker other : linkedList) {
			if (other.compareTo(worker) > 0) {
				linkedList.remove(other);
			}
		}
	}
	
	/**Очистка коллекции
	 */
	public void clear() {
		linkedList.clear();
	}
	
	/**Возвращает первый элемент коллекции (существует метод isEmpty, чтобы не допускать случая пустой коллекции)
	 * @return Worker первый работник в коллекции
	 */
	public Worker getHead() {
		return linkedList.getFirst();
	}
	
	/**Возвращает первый элемент коллекции (существует метод isEmpty, чтобы не допускать случая пустой коллекции)
	 * @return Worker первый работник в коллекции
	 */
	public LinkedList<Worker> getLinkedList() {
		return linkedList;
	}
	
	/**Возвращает связанный список работников с заданной зарплатой
	 * @param salary заданная зарплата
	 * @return LinkedList<Worker> работники с заданной зарплатой
	 */
	public LinkedList<Worker> getFilterBySalary(Float salary) {
		LinkedList<Worker> tmp = new LinkedList<>();
		for (Worker worker : linkedList) {
			if (worker.getSalary() != null && worker.getSalary().equals(salary)) {
				tmp.add(worker);
			}
		}
		return tmp;
	}
}