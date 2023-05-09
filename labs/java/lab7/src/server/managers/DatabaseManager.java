package server.managers;

import common.models.Person;
import common.models.Worker;
import common.models.User;

import java.sql.*;

public class DatabaseManager {
    private final String url;
    private final String login;
    private final String password;

    public DatabaseManager(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }

    public int addPerson(User user, Person person) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO persons(birthday, height, passportID, creator_id) " +
                            "VALUES (?, ?, ?," +
                            "(SELECT id FROM users WHERE users.name=?)) RETURNING id"
            );

            if (person.getBirthday() == null) statement.setNull(1, Types.DATE);
            else statement.setDate(1, Date.valueOf(person.getBirthday()));

            statement.setFloat(2, person.getHeight());

            statement.setString(3, person.getPassportID());
            statement.setString(4, user.getName());

            var result = statement.executeQuery();
            connection.close();

            result.next();

            return result.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWorker(User user, Worker worker) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO products(name, x, y, salary, pos, status, person_id, creator_id)" +
                            "VALUES (?, ?, ?, ?, ?::pos, ?::status, ?," +
                            "(SELECT id FROM users WHERE users.name=?)) RETURNING id"
            );

            statement.setString(1, worker.getName());
            statement.setInt(2, worker.getCoordinates().getX());
            statement.setInt(3, worker.getCoordinates().getY());

            if (worker.getSalary() == null) statement.setNull(4, Types.FLOAT);
            else statement.setFloat(4, worker.getSalary());

            if (worker.getPosition() == null) statement.setNull(5, Types.VARCHAR);
            else statement.setString(5, worker.getPosition().toString());

            if (worker.getStatus() == null) statement.setNull(6, Types.VARCHAR);
            else statement.setString(6, worker.getStatus().toString());

            statement.setString(7, user.getName());

            statement.executeQuery();

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
