package server.managers;

import common.consoles.Console;
import common.exceptions.NotUniqueIdException;
import common.models.Worker;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс для работы с коллекцией, который блокирует доступ к коллекции во время выполнения действий с ней
 */
public class BlockingCollectionManager extends CollectionManager {
    private final Lock lock;

    public BlockingCollectionManager(LinkedList<Worker> workers) {
        lock = new ReentrantLock();
        super.setWorkers(workers);
    }

    public void setConsole(Console console) {
        lock.lock();
        try {
            super.setConsole(console);
        } finally {
            lock.unlock();
        }
    }

    public void setWorkers(LinkedList<Worker> workers) {
        lock.lock();
        try {
            super.setWorkers(workers);
        } finally {
            lock.unlock();
        }
    }

    public void add(Worker worker) throws NotUniqueIdException {
        lock.lock();
        try {
            super.add(worker);
        } finally {
            lock.unlock();
        }
    }

    public void update(int id, Worker worker) {
        lock.lock();
        try {
            super.update(id, worker);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Удаляет работника из коллекции
     *
     * @param id работника
     */
    public void remove(int id) {
        lock.lock();
        try {
            super.remove(id);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Очистка коллекции
     */
    public void clear(int userId) {
        lock.lock();
        try {
            super.clear(userId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Пустая ли коллекция
     *
     * @return boolean true - коллекция пуста,false - в ней есть элементы
     */
    public boolean isEmpty() {
        lock.lock();
        try {
            return super.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Выводит информацию о коллекции
     */
    public void printInfo() {
        lock.lock();
        try {
            super.printInfo();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Выводит все элементы коллекции
     */
    public void printElements() {
        lock.lock();
        try {
            super.printElements();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Выводит элементы коллекции в порядке убывания
     */
    public void printDescending() {
        lock.lock();
        try {
            super.printDescending();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Выводит позиции работников, работники идут по убыванию
     */
    public void printFieldDescendingPosition() {
        lock.lock();
        try {
            super.printFieldDescendingPosition();
        } finally {
            lock.unlock();
        }
    }

    public boolean existsId(int id) {
        lock.lock();
        try {
            return super.existsId(id);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Удаляет работников, которые больше (по зарплате) заданного
     *
     * @param worker заданный работник
     */
    public void removeGreater(Worker worker, int userId) {
        lock.lock();
        try {
            super.removeGreater(worker, userId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Возвращает первый элемент коллекции (существует метод isEmpty, чтобы не допускать случая пустой коллекции)
     *
     * @return Worker первый работник в коллекции
     */
    public Worker getHead() {
        lock.lock();
        try {
            return super.getHead();
        } finally {
            lock.unlock();
        }
    }


    /**
     * Возвращает связанный список работников с заданной зарплатой
     *
     * @param salary заданная зарплата
     * @return LinkedList<Worker> работники с заданной зарплатой
     */
    public LinkedList<Worker> getFilterBySalary(Float salary) {
        lock.lock();
        try {
            return super.getFilterBySalary(salary);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Сортирует работников по имени
     */
    public void sortByName() {
        lock.lock();
        try {
            super.sortByName();
        }
        finally {
            lock.unlock();
        }
    }
}
