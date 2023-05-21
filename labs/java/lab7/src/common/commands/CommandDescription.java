package common.commands;

import common.models.Worker;

import java.io.Serializable;

public class CommandDescription implements Serializable {
    protected String name; //название команды
    protected String description;  //описание команды

    protected String[] args;  //аргументы при запуске команды
    protected boolean withWorker = false;  //нужен ли экземпляр работника для выполнения команды
    protected Worker worker;

    protected boolean onlyUsers = true;  //команду могут выполнять только зарегистрированные пользователи

    public CommandDescription(String name, String description, boolean withWorker, boolean onlyUsers) {
        this.name = name;
        this.description = description;
        this.withWorker = withWorker;
        this.onlyUsers = onlyUsers;
    }

    public CommandDescription(AbstractCommand command) {
        this(command.getName(), command.getDescription(), command.isWithWorker(), command.isOnlyUsers());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isWithWorker() {
        return withWorker;
    }

    public Worker getWorker() {
        return worker;
    }

    public boolean isOnlyUsers() {
        return onlyUsers;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        if (onlyUsers) return name + ": " + description + " (только для зарегистрированных пользователей)";
        else return name + ": " + description;
    }
}
