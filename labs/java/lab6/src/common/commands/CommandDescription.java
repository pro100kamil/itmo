package common.commands;

import common.models.Worker;

import java.io.Serializable;

/**
 * Класс абстрактной команды. Тут содержится общие поля и методы для серверных и клиентских команд
 */
public class CommandDescription implements Serializable {
    protected String name; //название команды
    protected String description;  //описание команды

    protected String[] args;  //аргументы при запуске команды
    protected boolean withWorker = false;  //нужен ли экземпляр работника для выполнения команды
    protected Worker worker;

    public CommandDescription(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
