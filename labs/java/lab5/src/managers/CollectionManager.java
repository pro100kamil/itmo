package managers;

import models.Worker;
import exceptions.NotUniqueIdException;

import java.util.LinkedList;
import java.util.Collections;
import java.util.TreeMap;
import java.time.LocalDateTime;

/**
 * ����� ��� ������ � ����������
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
		//���� ��������� ���������� id, ��������� ������ ���������
		//��������� ������ ���������� ����������
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
	 * ���������, ���������� �� � ��� ��������� ����������
	 * �� ���� ��� ��������� ���������� � ��� id ������
	 */
	public boolean validate() {
		for (Worker worker : linkedList) {
			if (!worker.validate() || idWorkerFromCollection.get(worker.getId()) != worker) {
				return false;
			}
		}
		return true;
	}
	
	/**������ �� ���������
	 * @return boolean true - ��������� �����,false - � ��� ���� ��������
	 */
	public boolean isEmpty() {
		return linkedList.size() == 0;
	}

	/**������� ���������� � ���������
	 */
	public void printInfo() {
		System.out.println("��� ������: " + linkedList.getClass().getName());
		System.out.println("���� �������������: " + creationDate);
		System.out.println("���������� ���������: " + linkedList.size());
	}
	
	/**����� ���� ��������� ���������
	 */
	public void printElements() {
		if (linkedList.size() == 0) {
			System.out.println("��������� ������");
			return;
		}
		System.out.println("�������� ���������: " + linkedList.size());
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
	 * ������� �������� ��������� � ������� ��������
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
	 * ������� ������� ����������, ��������� ���� �� ��������
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
	 * ��������� ��������� � ���������
	 * @param worker ��������, �������� �� ���������
	 */
	public void add(Worker worker) throws NotUniqueIdException {
		if (idWorkerFromCollection.containsKey(worker.getId())) { //���� ��� ���� ������������ � ����� id
			throw new NotUniqueIdException();
		}
		idWorkerFromCollection.put(worker.getId(), worker);
		linkedList.add(worker);
	}
	
	/**
	 * ��������� ��������� �� id �� ������ �������� ���������
	 * @param id ���������
	 * @param worker �������� ��������
	 */
	public void update(int id, Worker worker) {
		if (!idWorkerFromCollection.containsKey(worker.getId())) { //���� ��� ������������ � ����� id
			System.out.println("��� ������������ � ����� id!");
			return;
		}
		Worker w1 = idWorkerFromCollection.get(id);
		w1.update(worker);
	}
	
	/**
	 * ������� ��������� �� ���������
	 * @param id ���������
	 */
	public void remove(int id) {
		if (!idWorkerFromCollection.containsKey(id)) { //���� ��� ������������ � ����� id
			System.out.println("��� ������������ � ����� id!");
			return;
		}
		Worker worker = idWorkerFromCollection.get(id);
		linkedList.remove(worker);
	}
	
	/**
	 * ������� ����������, ������� ������ (�� ��������) ���������
	 * @param worker �������� ��������
	 */
	public void removeGreater(Worker worker) {
		for (Worker other : linkedList) {
			if (other.compareTo(worker) > 0) {
				linkedList.remove(other);
			}
		}
	}
	
	/**������� ���������
	 */
	public void clear() {
		linkedList.clear();
	}
	
	/**���������� ������ ������� ��������� (���������� ����� isEmpty, ����� �� ��������� ������ ������ ���������)
	 * @return Worker ������ �������� � ���������
	 */
	public Worker getHead() {
		return linkedList.getFirst();
	}
	
	/**���������� ������ ������� ��������� (���������� ����� isEmpty, ����� �� ��������� ������ ������ ���������)
	 * @return Worker ������ �������� � ���������
	 */
	public LinkedList<Worker> getLinkedList() {
		return linkedList;
	}
	
	/**���������� ��������� ������ ���������� � �������� ���������
	 * @param salary �������� ��������
	 * @return LinkedList<Worker> ��������� � �������� ���������
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