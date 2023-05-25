package server.managers;

import common.commands.CommandDescription;
import common.commands.ServerCommandDescription;
import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.UnavailableCommandException;
import common.exceptions.UnavailableModelException;
import common.exceptions.WrongCommandArgsException;
import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.models.User;
import common.models.UserRole;
import server.commands.*;
import server.managers.databaseManagers.CommandDatabaseManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Реализация класса CommandManager для серверной части.
 * Задаёт все команды.
 * Занимается запуском команды, серверной валидацией сохранением истории.
 */
public class CommandManager {
    private final ServerCommand[] serverCommands;

    private final TreeMap<String, ServerCommand> strToCommands = new TreeMap<>();
    //название команды, объект класса этой команды

    private final CollectionManager collectionManager;
    private final CollectionHistory collectionHistory;
    private final LinkedList<ServerCommand> history = new LinkedList<>();

    private final CommandDatabaseManager commandDatabaseManager;
    //история команд общая для всех пользователей

    Logger logger = new StandardLogger();

    public CommandManager(CollectionManager collectionManager,
                          CollectionHistory collectionHistory,
                          CommandDatabaseManager commandDatabaseManager) {
        this.collectionManager = collectionManager;
        this.collectionHistory = collectionHistory;
        this.commandDatabaseManager = commandDatabaseManager;

        serverCommands = new ServerCommand[]{new Info(),
                new Show(), new Add(),
                new Update(), new Remove(), new Clear(),
                new Head(), new RemoveGreater(),
                new History(), new FilterBySalary(),
                new PrintDescending(),
                new PrintFieldDescendingPosition(),
                new Rollback(),
                new Auth(), new Register(), new Logout(),
                new ChangeUserRole()
        };

        for (ServerCommand command : serverCommands) {
            strToCommands.put(command.getName(), command);
        }
    }

    /**
     * Задаёт всем командам минимальную роль, начиная с которой пользователь может выполнять команду.
     */
    public void setMinUserRoles() {
        for (ServerCommand command : serverCommands) {
            try {
                String minUserRole = commandDatabaseManager.getMinUserRole(command.getName());
                command.setMinUserRole(UserRole.valueOf(minUserRole));
            } catch (SQLException e) {
                logger.write("Нет информация о минимальной роли для команды " + command.getName());
            }
        }
    }

    /**
     * Получает описания всех серверных команд
     *
     * @return CommandDescription[] - массив описаний всех серверных команд
     */
    public CommandDescription[] getCommandDescriptions() {
        return Arrays.stream(serverCommands)
                .map(ServerCommandDescription::new)
                .toArray(CommandDescription[]::new);
    }

    /**
     * Получает описания всех серверных команд
     *
     * @return CommandDescription[] - массив описаний всех серверных команд
     */
    public CommandDescription[] getCommandDescriptions(UserRole userRole) {
        return Arrays.stream(serverCommands).filter(command -> command.getMinUserRole().ordinal() <= userRole.ordinal())
                .map(ServerCommandDescription::new)
                .toArray(CommandDescription[]::new);
    }

    /**
     * Получает описания серверных команд, которые доступны неавторизованному пользователю
     *
     * @return CommandDescription[] - массив описаний серверных команд
     */
    public CommandDescription[] getCommandDescriptionsForUnauthorizedUser() {
        return Arrays.stream(serverCommands).filter(command -> !command.isOnlyUsers())
                .map(ServerCommandDescription::new)
                .toArray(CommandDescription[]::new);
    }

    public ServerCommand getServerCommandFromCommandDescription(CommandDescription command) {
        ServerCommand serverCommand;
        //каждый раз создаём новый экземпляр
        try {
            serverCommand = strToCommands.get(command.getName()).getClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        serverCommand.setArgs(command.getArgs());
        serverCommand.setWorker(command.getWorker());

        return serverCommand;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CollectionHistory getCollectionHistory() {
        return collectionHistory;
    }

    public LinkedList<ServerCommand> getHistory() {
        return history;
    }

    /**
     * Добавить новое состояние в историю коллекции
     */
    public void addStateCollection() {
        collectionHistory.addStateCollection(collectionManager.getLinkedList());
    }

    /**
     * Проводит валидацию серверной команды
     *
     * @param command - конкретная команда
     */
    public void serverValidateCommand(ServerCommand command) throws NonExistentId, WrongCommandArgsException,
            UnavailableCommandException, UnavailableModelException {
        //если команда только для зарегистрированных пользователей, а текущий пользователь не вошёл в аккаунт,
        //то не даём ему провести валидацию и выполнить команду
        if (command.isOnlyUsers() && command.getUser() == null) {
            throw new UnavailableCommandException();
        }
        command.setCollectionManager(collectionManager);
        command.validateArgs(command.getArgs());
    }

    /**
     * Выполняет команду (аргументы уже хранятся внутри команды)
     *
     * @param command - конкретная команда
     */
    public void executeCommand(ServerCommand command, StringConsole strConsole) {
        command.setConsole(strConsole);
        command.setHistory(getHistory());
        command.setCollectionHistory(collectionHistory);
        command.setCollectionManager(collectionManager);

        String[] args = command.getArgs();
        //выполнение команды
        command.execute(args);
        //collectionHistory == null, если у нас работа с файлом
        if (collectionHistory != null) {
            //откат лишь отменяет команды, при нём не появляется новое состояние
            if (!(command instanceof Rollback)) {
                addStateCollection();
            }
            history.add(command);
        }
    }
}
