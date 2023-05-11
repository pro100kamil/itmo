package server.managers;

import common.models.User;

import java.sql.SQLException;

public class AuthManager {
    public static DatabaseManager databaseManager;

    public static boolean register(String name, String password) {
        User user = new User(name, password);
        try {
            if (!databaseManager.checkUserName(name)) {
                // если имя не занято, то создаём пользователя
                databaseManager.addUser(user);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    public static boolean auth(String name, String password) {
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
