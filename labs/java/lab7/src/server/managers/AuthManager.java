package server.managers;

import common.models.User;
import server.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {
    public DatabaseManager databaseManager = new DatabaseManager(Configuration.getDbUrl(),
            Configuration.getDbLogin(), Configuration.getDbPass());

    /**
     * Проверяет, есть ли пользователь с таким именем
     *
     * @param name - имя пользователя, которое проверяем
     * @return - true - есть, false - нет
     */
    public boolean checkUserName(String name) {
        try {
            return databaseManager.checkUserName(name);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * Проверяет, есть ли пользователь с таким именем и паролем
     *
     * @param name - имя пользователя, которое проверяем
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

    public boolean register(String name, String password) {
        User user = new User(name, password);
        try {
            if (!databaseManager.checkUserName(name)) {
                // если имя не занято, то создаём пользователя
                databaseManager.addUser(user);
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return false;
    }

    public boolean auth(String name, String password) {
        User user = new User(name, password);
        try {
            if (databaseManager.checkUserPass(name, password)) {
                //вход успешный
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
