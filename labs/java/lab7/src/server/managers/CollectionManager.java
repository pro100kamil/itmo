package server.managers;

import common.consoles.Console;
import common.consoles.StandardConsole;
import common.exceptions.NotUniqueIdException;
import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.models.Worker;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private Console console = new StandardConsole();
    private final Logger logger = new StandardLogger();
    private LinkedList<Worker> linkedList;
    private final TreeMap<Integer, Worker> idWorkerFromCollection = new TreeMap<>();
    private final LocalDateTime creationDate;

    protected CollectionManager() {
        creationDate = LocalDateTime.now();
        linkedList = new LinkedList<>();
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setWorkers(LinkedList<Worker> workers) {
        //если несколько одинаковых id, оставляем первый встречный
        //оставляем только корректных работников
        idWorkerFromCollection.clear();
        linkedList.clear();
        for (Worker worker : workers) {
            if (worker != null && worker.validate()) {
                idWorkerFromCollection.put(worker.getId(), worker);
                linkedList.add(worker);
            }
        }
    }

    /**
     * Добавляет работника в коллекцию
     *
     * @param worker работник, которого мы добавляем
     */
    public void add(Worker worker) throws NotUniqueIdException {
        idWorkerFromCollection.put(worker.getId(), worker);
        linkedList.add(worker);
    }

    /**
     * Обновляет работника по id на основе заданного работника
     *
     * @param id     работника
     * @param worker заданный работник
     */
    public void update(int id, Worker worker) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет работника с таким id
            console.write("Нет работника с таким id!");
            return;
        }
        Worker oldWorker = idWorkerFromCollection.get(id);

        worker.getPerson().setId(oldWorker.getId());

        oldWorker.update(worker);
    }

    /**
     * Удаляет работника из коллекции
     *
     * @param id id работника
     */
    public void remove(int id) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет работника с таким id
            console.write("Нет работника с таким id!");
            return;
        }

        linkedList.remove(idWorkerFromCollection.get(id));
        idWorkerFromCollection.remove(id);
    }

    /**
     * Очистка коллекции (удаляются работники конкретного пользователя)
     *
     * @param userId id конкретного пользователя
     */
    public void clear(int userId) {
        for (Integer id : (new TreeMap<>(idWorkerFromCollection)).keySet()) {
            Worker worker = idWorkerFromCollection.get(id);
            if (worker.getCreatorId() == userId) {
                linkedList.remove(worker);
                idWorkerFromCollection.remove(id);
            }
        }
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
        linkedList.stream().sorted(Comparator.reverseOrder()).forEach(worker ->
                console.write(worker.getPosition() == null ? "null" : worker.getPosition().toString()));
    }

    public boolean existsId(int id) {
        return idWorkerFromCollection.containsKey(id);
    }

    /**
     * Удаляет работников, которые больше (по зарплате) заданного (удаляются работники конкретного пользователя)
     *      *
     *      * @param userId id конкретного пользователя
     */
    public void removeGreater(Worker worker, int userId) {
        for (Worker other : new LinkedList<>(linkedList)) {
            //other > worker и other принадлежит текущему юзеру => удаляем other
            if (other.getCreatorId() == userId && other.compareTo(worker) > 0) {
                remove(other.getId());
            }
        }
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

    public Worker getWorkerById(int id) {
        return idWorkerFromCollection.get(id);
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