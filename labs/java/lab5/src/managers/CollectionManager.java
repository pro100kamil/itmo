package managers;

import models.Position;
import models.Worker;
import exceptions.NotUniqueIdException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;
import java.time.LocalDateTime;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private Console console = new ConsoleManager();
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
        setWorkers(workers);
    }

    public void setWorkers(LinkedList<Worker> workers) {
        //если несколько одинаковых id, оставляем первый встречный
        //оставляем только корректных работников
        clear();
        Integer maxId = 0;
        for (Worker worker : workers) {
            if (worker != null && worker.validate()) {
                try {
                    add(worker);
                    maxId = Math.max(maxId, worker.getId());
                } catch (NotUniqueIdException e) {

                }
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
     * Вывод всех элементов коллекции
     */
    public void printElements() {
        if (linkedList.size() == 0) {
            console.write("Коллекция пустая");
            return;
        }
        console.write("Элементы коллекции: " + linkedList.size());
        int i = 0;
        for (Worker worker : linkedList) {
            System.out.print(worker);
            if (i != linkedList.size() - 1) {
                console.write(", ");
            }
            i += 1;
        }
        console.write(".");
    }

    /**
     * Выводит элементы коллекции в порядке убывания
     */
    public void printDescending() {
        LinkedList<Worker> copyList = new LinkedList<>(linkedList);
        Collections.sort(copyList);
        Collections.reverse(copyList);
        for (Worker worker : copyList) {
            console.write(worker.toString());
        }
    }

    /**
     * Выводит позиции работников, работники идут по убыванию
     */
    public void printFieldDescendingPosition() {
        LinkedList<Worker> copyList = new LinkedList<>(linkedList);
        Collections.sort(copyList);
        Collections.reverse(copyList);
        for (Worker worker : copyList) {
            Position pos = worker.getPosition();
            if (pos != null) console.write(pos.toString());
            else console.write("null");
        }
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
        Worker w1 = idWorkerFromCollection.get(id);
        w1.update(worker);

    }

    /**
     * Удаляем работника из коллекции
     *
     * @param id работника
     */
    public void remove(int id) {
        if (!idWorkerFromCollection.containsKey(id)) { //если нет пользователя с таким id
            console.write("Нет пользователя с таким id!");
            return;
        }
        Worker worker = idWorkerFromCollection.get(id);
        idWorkerFromCollection.remove(id);
        linkedList.remove(worker);
    }

    /**
     * Удаляет работников, которые больше (по зарплате) заданного
     *
     * @param worker заданный работник
     */
    public void removeGreater(Worker worker) {
        for (Worker other : new LinkedList<Worker>(linkedList)) {
            //other > worker
            if (other.compareTo(worker) > 0) {
                idWorkerFromCollection.remove(other.getId());
                linkedList.remove(other);
            }
        }
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
     * Возвращает копию связанного список работников
     *
     * @return LinkedList<Worker> копия связанного списка работников (текущего состояние коллекции)
     */
    public LinkedList<Worker> copy() {
        LinkedList<Worker> res = new LinkedList<>();
        for (Worker worker : linkedList) {
            res.add(worker.copy());
        }
        return res;
    }

    /**
     * Возвращает связанный список работников с заданной зарплатой
     *
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