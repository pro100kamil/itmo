package server.managers;

import common.exceptions.NotUniqueIdException;
import common.models.Worker;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BlockingCollectionManager extends CollectionManager {
    private final Lock lock = new ReentrantLock();

    public BlockingCollectionManager(DatabaseManager databaseManager, LinkedList<Worker> workers) {
        super(databaseManager, workers);
    }

    /**
     * Добавляет работника в коллекцию
     *
     * @param worker работник, которого мы добавляем
     */
    @Override
    public void add(Worker worker) throws NotUniqueIdException {
        lock.lock();
        try {
            super.add(worker);
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
    public void clear() {
        lock.lock();
        try {
            super.clear();
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

    /**
     * Удаляет работников, которые больше (по зарплате) заданного
     *
     * @param worker заданный работник
     */
    public void removeGreater(Worker worker) {
        lock.lock();
        try {
            super.removeGreater(worker);
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

}
