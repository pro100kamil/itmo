package server.managers.databaseManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для взаимодействий с таблицей команд из базы данных.
 */
public class CommandDatabaseManager {
    private final ConnectionManager connectionManager;

    public CommandDatabaseManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    public CommandDatabaseManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    public String getMinUserRole(String commandName) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT min_user_role FROM commands WHERE name = ?");

        statement.setString(1, commandName);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getString("min_user_role");
    }
}
