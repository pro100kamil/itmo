package server.managers;

import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.models.User;
import server.Configuration;
import server.managers.databaseManagers.ConnectionManager;
import server.managers.databaseManagers.UserDatabaseManager;

import java.sql.SQLException;

public class AuthManager {
    private static final Logger logger = new StandardLogger();
    private final UserDatabaseManager databaseManager = new UserDatabaseManager(
            new ConnectionManager(Configuration.getDbUrl(), Configuration.getDbLogin(), Configuration.getDbPass()));

    /**
     * Проверяет, есть ли пользователь с таким именем
     *
     * @param name - имя пользователя, которое проверяем
     * @return - true - есть, false - нет
     */
    public boolean checkUserName(String name) {
        return databaseManager.checkUserName(name);
    }


    /**
     * Проверяет, есть ли пользователь с таким именем и паролем
     *
     * @param name     - имя пользователя, которое проверяем
     * @param password - пароль пользователя, который проверяем
     * @return - true - есть (авторизация прошла успешно), false - нет
     */
    public boolean checkUserPass(String name, String password) {
        try {
            return databaseManager.checkUserPass(name, password);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean register(User user) {
        try {
            if (!databaseManager.checkUserName(user.getName())) {
                // если имя не занято, то создаём пользователя
                int id = databaseManager.addUser(user);
                user.setId(id);
                logger.write("Регистрация прошла успешно");
                return true;
            }
        } catch (SQLException e) {
            logger.write("Не получилось зарегистрироваться");
            logger.writeError(e.toString());
            return false;
        }
        return false;
    }

    public boolean auth(User user) {
        try {
            if (databaseManager.checkUserPass(user.getName(), user.getPassword())) {
                //вход успешный
                int id = databaseManager.getUserId(user.getName());
                user.setId(id);
                logger.write("Вход прошёл успешно");
                return true;
            }
        } catch (SQLException e) {
            logger.write("Не получилось войти");
            logger.writeError(e.toString());
            return false;
        }
        return false;
    }
}
