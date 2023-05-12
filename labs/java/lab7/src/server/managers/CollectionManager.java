package server.managers;

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
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private Console console = new StandardConsole();

    private final DatabaseManager databaseManager;
    private final User user;
    private LinkedList<Worker> linkedList;
    private final TreeMap<Integer, Worker> idWorkerFromCollection = new TreeMap<>();
    private final LocalDateTime creationDate;

    public CollectionManager(DatabaseManager databaseManager, User user) {
        this.databaseManager = databaseManager;
        this.user = user;
        creationDate = LocalDateTime.now();
        linkedList = new LinkedList<>();
    }

    public CollectionManager(DatabaseManager databaseManager, User user, LinkedList<Worker> workers) {
        //если несколько одинаковых id, оставляем первый встречный
        //оставляем только корректных работников
        this(databaseManager, user);
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
                } catch (NotUniqueIdException ignored) {
                }
            }
        }
//        Worker.moveNextId(maxId + 1);
    }

    /**
     * Добавляет работника в коллекцию
     *
     * @param worker работник, которого мы добавляем
     */
    public void add(Worker worker) throws NotUniqueIdException {
        if (worker.getId() == 0) {  //добавляем в бд
            try {
                int id = databaseManager.addWorker(user, worker);
                worker.setId(id);
            } catch (SQLException e) {
                console.write("Добавить работника не получилось");
                console.write(e.toString());
                throw new RuntimeException(e);
//                return;
            }
        }
        //добавляем в коллекции
        idWorkerFromCollection.put(worker.getId(), worker);
        linkedList.add(worker);
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
        worker.setId(id);
        try {
            databaseManager.updateWorker(user, worker);
        } catch (SQLException e) {
            console.write("Обновить работника не получилось");
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
        try {
            databaseManager.removeWorker(user, idWorkerFromCollection.get(id));
        } catch (SQLException e) {
            console.write("Удалить работника не получилось");
            return;
        }
        linkedList.remove(idWorkerFromCollection.get(id));
        idWorkerFromCollection.remove(id);
    }

    /**
     * Очистка коллекции
     */
    public void clear() {
//        TODO clear
        try {
            databaseManager.clearWorkers(user);
        } catch (SQLException e) {
            console.write("Очистить коллекцию не получилось");
            return;
        }
        idWorkerFromCollection.clear();
        linkedList.clear();
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
        linkedList.stream().sorted(Comparator.reverseOrder()).forEach(worker ->
                console.write(worker.getPosition() == null ? "null" : worker.getPosition().toString()));
    }

    public boolean existsId(int id) {
        return idWorkerFromCollection.containsKey(id);
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