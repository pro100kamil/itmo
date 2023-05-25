package server.managers.databaseManagers;

import common.models.User;
import common.models.UserRole;
import server.managers.PasswordManager;
import server.models.ServerUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для взаимодействий с таблицей пользователей из базы данных.
 */
public class UserDatabaseManager {
    private final ConnectionManager connectionManager;

    public UserDatabaseManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    public UserDatabaseManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    /**
     * Изменяет роль пользователя
     *
     * @param userId id пользователя
     * @param userRole новая роль пользователя
     * @return int число изменённых записей в бд (0 - роль не изменилась, 1 - роль изменилась)
     */
    public int changeUserRole(int userId, UserRole userRole) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET role = ? " +
                "WHERE id = ?"
        );

        statement.setString(1, userRole.toString());
        statement.setInt(2, userId);

        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    /**
     * Добавляет пользователя в базу данных
     *
     * @param user пользователь, которого добавляем
     * @return int id добавленного пользователя
     */
    public int addUser(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO USERS(name, password_digest, salt, role)" +
                "VALUES (?, ?, ?, 'USER_MIN') RETURNING id");

        statement.setString(1, user.getName());
        String salt = PasswordManager.getSalt();
        statement.setString(2, PasswordManager.getHash(user.getPassword(), salt));
        statement.setString(3, salt);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt(1);
    }

    //TODO переделать структуру методов
    /**
     * Получает пользователя по имени
     *
     * @param id id пользователя
     * @return true - есть, false - нет
     */
    public ServerUser getUser(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?");

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        String name = result.getString("name");
        String password_digest = result.getString("password_digest");
        String salt = result.getString("salt");
        String role = result.getString("role");


        return new ServerUser(id, name, password_digest, salt, UserRole.valueOf(role));
    }


    /**
     * Получает пользователя по имени
     *
     * @param name - имя пользователя
     * @return - true - есть, false - нет
     */
    public ServerUser getUser(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE name = ?");

        statement.setString(1, name);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        int id = result.getInt("id");
        String password_digest = result.getString("password_digest");
        String salt = result.getString("salt");
        String role = result.getString("role");


        return new ServerUser(id, name, password_digest, salt, UserRole.valueOf(role));
    }

    public ServerUser getUser(String name, String password) throws SQLException {
        ServerUser user = getUser(name);
        String salt = user.getSalt();
        String password_digest = PasswordManager.getHash(password, salt);
        if (password_digest.equals(user.getPasswordDigest())) {
            return user;
        }
        return null;
    }

    /**
     * Получает соль по имени пользователя
     *
     * @param name - имя пользователя
     * @return String - соль пользователя
     */
    public String getUserSalt(String name) throws SQLException {
        ServerUser user = getUser(name);

        return user.getSalt();
    }

    /**
     * Получает id по имени пользователя
     *
     * @param name - имя пользователя
     * @return int - id пользователя
     */
    public int getUserId(String name) throws SQLException {
        ServerUser user = getUser(name);

        return user.getId();
    }

    /**
     * Получает роль по имени пользователя
     *
     * @param name - имя пользователя
     * @return UserRole - роль пользователя
     */
    public UserRole getUserRole(String name) throws SQLException {
        ServerUser user = getUser(name);

        return user.getRole();
    }

   public boolean isAdmin(int id) {
       try {
           ServerUser user = getUser(id);
           return user.getRole().equals(UserRole.ADMIN);
       } catch (SQLException e) {
           return false;
       }
   }

    /**
     * Проверяет, есть ли пользователь с таким именем
     *
     * @param name - имя пользователя, которое проверяем
     * @return - true - есть, false - нет
     */
    public boolean checkUserName(String name) {
        ServerUser user = null;
        try {
            user = getUser(name);
        } catch (SQLException e) {
            return false;
        }
        return user != null;
    }

    /**
     * Проверяет, есть ли пользователь с таким именем
     *
     * @param id - имя пользователя, которое проверяем
     * @return - true - есть, false - нет
     */
    public boolean checkUserId(int id) {
        ServerUser user = null;
        try {
            user = getUser(id);
        } catch (SQLException e) {
            return false;
        }
        return user != null;
    }

    /**
     * Проверяет, есть ли пользователь с таким именем и паролем
     *
     * @param name     - имя пользователя, которое проверяем
     * @param password - пароль пользователя, который проверяем
     * @return - true - есть (авторизация прошла успешно), false - нет
     */
    public boolean checkUserPass(String name, String password) throws SQLException {
        ServerUser user = getUser(name, password);
        return user != null;
    }
}
