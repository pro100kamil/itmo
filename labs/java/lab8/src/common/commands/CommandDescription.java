package common.commands;

import common.models.UserRole;
import common.models.Worker;

import java.io.Serializable;
import java.util.Arrays;

public class CommandDescription implements Serializable {
    protected String name; //название команды
    protected String description;  //описание команды

    protected String[] args;  //аргументы при запуске команды
    protected boolean withWorker = false;  //нужен ли экземпляр работника для выполнения команды
    protected Worker worker;

    protected boolean onlyUsers = true;  //команду могут выполнять только зарегистрированные пользователи
    protected UserRole minUserRole;

    public CommandDescription(String name) {
        this.name = name;
    }

    public CommandDescription(String name, String description, boolean withWorker,
                              boolean onlyUsers, UserRole minUserRole) {
        this.name = name;
        this.description = description;
        this.withWorker = withWorker;
        this.onlyUsers = onlyUsers;
        this.minUserRole = minUserRole;
    }

    public CommandDescription(AbstractCommand command) {
        this(command.getName(), command.getDescription(), command.isWithWorker(),
                command.isOnlyUsers(), command.getMinUserRole());
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

    public UserRole getMinUserRole() {
        return minUserRole;
    }

    public void setMinUserRole(UserRole minUserRole) {
        this.minUserRole = minUserRole;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }

//
//    @Override
//    public String toString() {
//        return "CommandDescription{" +
//               "name='" + name + '\'' +
//               ", description='" + description + '\'' +
//               ", args=" + Arrays.toString(args) +
//               ", withWorker=" + withWorker +
//               ", worker=" + worker +
//               ", onlyUsers=" + onlyUsers +
//               ", minUserRole='" + minUserRole + '\'' +
//               '}';
//    }
}
