package common.commands;

import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import common.models.Worker;

import java.io.Serializable;

/**
 * Класс абстрактной команды. Тут содержится общие поля и методы для серверных и клиентских команд
 */
public class AbstractCommand implements Serializable {
    protected String name; //название команды
    protected String description;  //описание команды

    protected String[] args;  //аргументы при запуске команды
    protected boolean withWorker = false;  //нужен ли экземпляр работника для выполнения команды
    protected Worker worker;

    protected boolean onlyUsers = true;  //команду могут выполнять только зарегистрированные пользователи

    public AbstractCommand(String name, String description, boolean withWorker, boolean onlyUsers) {
        this.name = name;
        this.description = description;
        this.withWorker = withWorker;
        this.onlyUsers = onlyUsers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public boolean isWithWorker() {
        return withWorker;
    }

    public void setWithWorker(boolean withWorker) {
        this.withWorker = withWorker;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public boolean isOnlyUsers() {
        return onlyUsers;
    }

    /**
     * Проверяет аргументы на корректность. При неправильных аргументах бросает соответсвующее исключение.
     *
     * @param args - аргументы команды
     */
    public void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId {

    };

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
