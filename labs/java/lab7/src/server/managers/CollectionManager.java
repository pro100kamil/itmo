package server.managers;

import common.exceptions.UnavailableModelException;
import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.models.User;
import common.models.Worker;
import common.exceptions.NotUniqueIdException;
import common.consoles.Console;
import common.consoles.StandardConsole;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private Console console = new StandardConsole();
    private final Logger logger = new StandardLogger();
    private final DatabaseManager databaseManager;
    private LinkedList<Worker> linkedList;
    private final TreeMap<Integer, Worker> idWorkerFromCollection = new TreeMap<>();
    private final LocalDateTime creationDate;

    private final Lock lock = new ReentrantLock();

    protected CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        creationDate = LocalDateTime.now();
        linkedList = new LinkedList<>();
    }

//    protected CollectionManager(DatabaseManager databaseManager, LinkedList<Worker> workers) {
//        //если несколько одинаковых id, оставляем первый встречный
//        //оставляем только корректных работников
//        this(databaseManager);
//        this.setWorkers(workers);
//    }

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
    public void add(Worker worker, User user) throws NotUniqueIdException {
        lock.lock();
        try {
            if (worker.getId() == 0) {  //добавляем в бд
                try {
                    int id = databaseManager.addWorker(user, worker);
                    worker.setId(id);
                    worker.setCreatorId(user.getId());
                    worker.getPerson().setCreatorId(user.getId());
                } catch (SQLException e) {
                    console.write("Добавить работника не получилось");
                    logger.writeError("Добавить работника не получилось: " + e);
                    return;
                }
            }
            //добавляем в коллекции
            idWorkerFromCollection.put(worker.getId(), worker);
            linkedList.add(worker);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Обновляет работника по id на основе заданное работника
     *
     * @param id     работника
     * @param worker заданный работник
     */
    public void update(int id, Worker worker, User user) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет работника с таким id
            console.write("Нет работника с таким id!");
            return;
        }
        Worker oldWorker = idWorkerFromCollection.get(id);

        worker.setId(id);
        worker.getPerson().setId(oldWorker.getId());

        try {
            int kol = databaseManager.updateWorker(user, worker);
            if (kol == 0) {
                throw new UnavailableModelException();
            }
        } catch (SQLException | UnavailableModelException e) {
            console.write("Обновить работника не получилось");
            logger.writeError("Обновить работника не получилось: " + e);
            return;
        }

        oldWorker.update(worker);
    }

    /**
     * Удаляет работника из коллекции
     *
     * @param id работника
     */
    public void remove(int id, User user) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет пользователя с таким id
            console.write("Нет пользователя с таким id!");
            return;
        }
        try {
            int kol = databaseManager.removeWorker(user, idWorkerFromCollection.get(id));
            if (kol == 0) {
                throw new UnavailableModelException();
            }
        } catch (SQLException | UnavailableModelException e) {
            console.write("Удалить работника не получилось");
            logger.writeError("Удалить работника не получилось: " + e);
            return;
        }
        linkedList.remove(idWorkerFromCollection.get(id));
        idWorkerFromCollection.remove(id);
    }

    /**
     * Очистка коллекции
     */
    public void clear(User user) {
        try {
            int kol = databaseManager.clearWorkers(user);
            console.write("Удалено работников: " + kol);
        } catch (SQLException e) {
            console.write("Очистить коллекцию не получилось");
            logger.writeError("Очистить коллекцию не получилось: " + e);
            return;
        }
        idWorkerFromCollection.clear();
        linkedList.clear();
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
        return !idWorkerFromCollection.containsKey(id);
    }

    /**
     * Удаляет работников, которые больше (по зарплате) заданного
     *
     * @param worker заданный работник
     */
    public void removeGreater(Worker worker) {
        for (Worker other : new LinkedList<>(linkedList)) {
            //other > worker => удаляем other
            if (other.compareTo(worker) > 0) {
                idWorkerFromCollection.remove(other.getId());
                linkedList.remove(other);
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