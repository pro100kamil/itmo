package server.managers;

import common.models.Worker;
import common.exceptions.NotUniqueIdException;
import common.consoles.Console;
import common.consoles.StandardConsole;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private Console console = new StandardConsole();
    private LinkedList<Worker> linkedList;
    private final TreeMap<Integer, Worker> idWorkerFromCollection = new TreeMap<>();
    private final LocalDateTime creationDate;

    public CollectionManager() {
        creationDate = LocalDateTime.now();
        linkedList = new LinkedList<>();
    }

    public CollectionManager(LinkedList<Worker> workers) {
        //если несколько одинаковых id, оставляем первый встречный
        //оставляем только корректных работников
        this();
        setWorkers(workers);
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setWorkers(LinkedList<Worker> workers) {
        //если несколько одинаковых id, оставляем первый встречный
        //оставляем только корректных работников
        clear();
        int maxId = 0;
        for (Worker worker : workers) {
            if (worker != null && worker.validate()) {
                try {
                    add(worker);
                    maxId = Math.max(maxId, worker.getId());
                } catch (NotUniqueIdException ignored) {}
            }
        }
        Worker.moveNextId(maxId + 1);
    }

    /**
     * Проверяет, корректная ли у нас коллекция работников.
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

    /**
     * Пустая ли коллекция
     *
     * @return boolean true - коллекция пуста,false - в ней есть элементы
     */
    public boolean isEmpty() {
        return linkedList.size() == 0;
    }

    /**
     * Выводит информацию о коллекции
     */
    public void printInfo() {
        console.write("Тип данных: " + linkedList.getClass().getName());
        console.write("Дата инициализации: " + creationDate);
        console.write("Количество элементов: " + linkedList.size());
    }

    /**
     * Выводит все элементы коллекции
     */
    public void printElements() {
        if (linkedList.size() == 0) {
            console.write("Коллекция пустая");
            return;
        }
        console.write("Элементы коллекции: " + linkedList.size());
        linkedList.forEach(worker -> console.write(worker.toString()));
    }

    /**
     * Выводит элементы коллекции в порядке убывания
     */
    public void printDescending() {
        linkedList.stream().sorted(Comparator.reverseOrder()).forEach(worker -> console.write(worker.toString()));
    }

    /**
     * Выводит позиции работников, работники идут по убыванию
     */
    public void printFieldDescendingPosition() {
        linkedList.stream().sorted(Comparator.reverseOrder()).forEach(worker -> console.write(worker.getPosition().toString()));
    }

    /**
     * Добавляет работника в коллекцию
     *
     * @param worker работник, которого мы добавляем
     */
    public void add(Worker worker) throws NotUniqueIdException {
        if (existsId(worker.getId())) { //если уже есть пользователь с таким id
            throw new NotUniqueIdException();
        }
        idWorkerFromCollection.put(worker.getId(), worker);
        linkedList.add(worker);
    }

    public boolean existsId(int id) {
        return idWorkerFromCollection.containsKey(id);
    }

    /**
     * Обновляет работника по id на основе заданное работника
     *
     * @param id     работника
     * @param worker заданный работник
     */
    public void update(int id, Worker worker) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет пользователя с таким id
            console.write("Нет пользователя с таким id!");
            return;
        }
        idWorkerFromCollection.get(id).update(worker);

    }

    /**
     * Удаляет работника из коллекции
     *
     * @param id работника
     */
    public void remove(int id) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет пользователя с таким id
            console.write("Нет пользователя с таким id!");
            return;
        }
        linkedList.remove(idWorkerFromCollection.get(id));
        idWorkerFromCollection.remove(id);
    }

    /**
     * Удаляет работников, которые больше (по зарплате) заданного
     *
     * @param worker заданный работник
     */
    public void removeGreater(Worker worker) {
        //other > worker => удаляем other
        linkedList.stream().filter(other -> other.compareTo(worker) > 0).forEach(other -> {
            idWorkerFromCollection.remove(other.getId());
            linkedList.remove(other);
        });
    }

    /**
     * Очистка коллекции
     */
    public void clear() {
        idWorkerFromCollection.clear();
        linkedList.clear();
    }

    /**
     * Возвращает первый элемент коллекции (существует метод isEmpty, чтобы не допускать случая пустой коллекции)
     *
     * @return Worker первый работник в коллекции
     */
    public Worker getHead() {
        return linkedList.getFirst();
    }

    /**
     * Возвращает связанный список работников
     *
     * @return LinkedList<Worker> связанный список работников (текущее состояние коллекции)
     */
    public LinkedList<Worker> getLinkedList() {
        return linkedList;
    }

    /**
     * Возвращает связанный список работников с заданной зарплатой
     *
     * @param salary заданная зарплата
     * @return LinkedList<Worker> работники с заданной зарплатой
     */
    public LinkedList<Worker> getFilterBySalary(Float salary) {
        return linkedList.stream().filter(worker ->
                (worker.getSalary() == null && salary == null)
                        || (worker.getSalary() != null && worker.getSalary().equals(salary))
        ).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Сортирует работников по имени
     */
    public void sortByName() {
        linkedList = linkedList.stream().sorted(Comparator.comparing(Worker::getName))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}