package server.managers.databaseManagers;

import common.models.*;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Класс для взаимодействий с таблицей работников и людей из базы данных.
 */
public class WorkerDatabaseManager {
    ConnectionManager connectionManager;

    public WorkerDatabaseManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    public WorkerDatabaseManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    /**
     * Загружает объектов Person из базы данных
     *
     * @return Map<Integer, Person> - соответствие id и человека
     */
    public Map<Integer, Person> loadPersons() throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM persons");
        ResultSet result = statement.executeQuery();

        Map<Integer, Person> persons = new HashMap<Integer, Person>();
        while (result.next()) {
            Integer id = result.getInt("id");
            Person person = new Person(
                    result.getDate("birthday") == null ? null : result.getDate("birthday").toLocalDate(),
                    result.getFloat("height"), result.getString("passportID"));
            person.setId(id);
            person.setCreatorId(result.getInt("creator_id"));
            persons.put(id, person);
        }

        connection.close();
        return persons;
    }

    public List<Worker> loadWorkers() throws SQLException {
        Map<Integer, Person> persons = loadPersons();
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM workers");
        ResultSet result = statement.executeQuery();

        List<Worker> workers = new LinkedList<>();
        while (result.next()) {
            int personId = result.getInt("person_id");
            Person person = persons.get(personId);

            Worker worker = new Worker(result.getInt("id"), result.getString("name"), new Coordinates(result.getInt("x"), result.getInt("y")), result.getTimestamp("creation_date").toLocalDateTime(), result.getFloat("salary"), result.getString("pos") == null ? null : Position.valueOf(result.getString("pos")), result.getString("status") == null ? null : Status.valueOf(result.getString("status")), person);
            worker.setCreatorId(result.getInt("creator_id"));
            workers.add(worker);
        }

        connection.close();
        return workers;
    }

    public int addPerson(User user, Person person) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO persons(birthday, height, " +
                "passportID, creator_id) " +
                "VALUES (?, ?, ?," +
                "(SELECT id FROM users WHERE users.name=?)) " +
                "RETURNING id");

        if (person.getBirthday() == null) statement.setNull(1, Types.DATE);
        else statement.setDate(1, Date.valueOf(person.getBirthday()));

        statement.setFloat(2, person.getHeight());

        if (person.getPassportID() == null) statement.setNull(3, Types.VARCHAR);
        else statement.setString(3, person.getPassportID());

        statement.setString(4, user.getName());

        ResultSet result = statement.executeQuery();
        connection.close();

        result.next();

        return result.getInt(1);
    }

    /**
     * Добавляет работника в базу данных
     *
     * @param user   - пользователь, который добавляет работника
     * @param worker - работник, которого добавляют
     * @return int - id, которое получит добавленный работник
     */
    public int addWorker(User user, Worker worker) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO workers(name, x, y, salary, pos," +
                " status, person_id, creator_id)" +
                "VALUES (?, ?, ?, ?, ?::pos, ?::status, ?," +
                "(SELECT id FROM users WHERE users.name=?)) " +
                "RETURNING id"
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

        int person_id = addPerson(user, worker.getPerson());
        worker.getPerson().setId(person_id);

        statement.setInt(7, person_id);

        statement.setString(8, user.getName());

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt(1);
    }

    public int updatePerson(User user, int personId, Person newPerson) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE persons SET birthday = ?, height = ?," +
                "passportID = ?" +
                "WHERE id = ? AND creator_id = ?"
        );

        if (newPerson.getBirthday() == null) statement.setNull(1, Types.DATE);
        else statement.setDate(1, Date.valueOf(newPerson.getBirthday()));

        statement.setFloat(2, newPerson.getHeight());

        statement.setString(3, newPerson.getPassportID());

        statement.setInt(4, personId);
        statement.setInt(5, user.getId());

        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    public int updateWorker(User user, int workerId, Worker newWorker) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE workers SET name = ?, x = ?, y = ?, salary = ?, " +
                "pos = ?::pos, status = ?::status, person_id = ? " +
                "WHERE id = ? AND creator_id = ?"
        );

        statement.setString(1, newWorker.getName());
        statement.setInt(2, newWorker.getCoordinates().getX());
        statement.setInt(3, newWorker.getCoordinates().getY());

        if (newWorker.getSalary() == null) statement.setNull(4, Types.FLOAT);
        else statement.setFloat(4, newWorker.getSalary());

        if (newWorker.getPosition() == null) statement.setNull(5, Types.VARCHAR);
        else statement.setString(5, newWorker.getPosition().toString());

        if (newWorker.getStatus() == null) statement.setNull(6, Types.VARCHAR);
        else statement.setString(6, newWorker.getStatus().toString());

        updatePerson(user, workerId, newWorker.getPerson());
        statement.setInt(7, newWorker.getPerson().getId());

        statement.setInt(8, workerId);
        statement.setInt(9, user.getId());

        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    public int clearWorkers(User user) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement_workers = connection.prepareStatement(
                "DELETE FROM workers WHERE creator_id = ?"
        );
        statement_workers.setInt(1, user.getId());
        statement_workers.executeUpdate();

        PreparedStatement statement_persons = connection.prepareStatement(
                "DELETE FROM persons WHERE creator_id = ?"
        );
        statement_persons.setInt(1, user.getId());
        int res = statement_persons.executeUpdate();

        connection.close();
        return res;
    }

    public int removeWorker(User user, Worker worker) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement_worker = connection.prepareStatement(
                "DELETE FROM workers WHERE creator_id = ? AND id = ?"
        );

        statement_worker.setInt(1, user.getId());
        statement_worker.setInt(2, worker.getId());
        int res = statement_worker.executeUpdate();

        PreparedStatement statement_person = connection.prepareStatement(
                "DELETE FROM persons WHERE creator_id = ? AND id = ?"
        );
        statement_person.setInt(1, user.getId());
        statement_person.setInt(2, worker.getPerson().getId());
        statement_person.executeUpdate();

        connection.close();
        return res;
    }
}
