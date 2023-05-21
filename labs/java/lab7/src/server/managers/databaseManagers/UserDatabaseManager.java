package server.managers.databaseManagers;

import common.models.User;
import server.managers.PasswordManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, отвечающий
 */
public class UserDatabaseManager {
    ConnectionManager connectionManager;

    public UserDatabaseManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    /**
     * Проверяет, есть ли пользователь с таким именем
     *
     * @param name - имя пользователя, которое проверяем
     * @return - true - есть, false - нет
     */
    public boolean checkUserName(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE name = ?");

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        connection.close();

        return result.next();
    }

    /**
     * Проверяет, есть ли пользователь с таким именем и паролем
     *
     * @param name     - имя пользователя, которое проверяем
     * @param password - пароль пользователя, который проверяем
     * @return - true - есть (авторизация прошла успешно), false - нет
     */
    public boolean checkUserPass(String name, String password) throws SQLException {
        String salt = getUserSalt(name);
        String password_digest = PasswordManager.getHash(password, salt);

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE name = ? AND password_digest = ?");

        statement.setString(1, name);
        statement.setString(2, password_digest);

        ResultSet result = statement.executeQuery();

        connection.close();

        return result.next();
    }

    /**
     * Получает соль по имени пользователя
     *
     * @param name - имя пользователя
     * @return String - соль пользователя
     */
    public String getUserSalt(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT salt FROM users WHERE name = ?");

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getString("salt");
    }

    /**
     * Получает id по имени пользователя
     *
     * @param name - имя пользователя
     * @return int - id пользователя
     */
    public int getUserId(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM users WHERE name = ?");

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt("id");
    }

    public int addUser(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO USERS(name, password_digest, salt, role)" +
                "VALUES (?, ?, ?, 'user_min') RETURNING id");

        statement.setString(1, user.getName());
        String salt = PasswordManager.getSalt();
        statement.setString(2, PasswordManager.getHash(user.getPassword(), salt));
        statement.setString(3, salt);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt(1);
    }
}
