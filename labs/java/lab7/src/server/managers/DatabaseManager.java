package server.managers;

import common.models.*;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Класс для взаимодействий с базой данных.
 */
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
     * @param name - имя пользователя, которое проверяем
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
                "INSERT INTO USERS(name, password_digest, salt)" +
                "VALUES (?, ?, ?) RETURNING id");

        statement.setString(1, user.getName());
        String salt = PasswordManager.getSalt();
        statement.setString(2, PasswordManager.getHash(user.getPassword(), salt));
        statement.setString(3, salt);

        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt(1);
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

    public int updatePerson(User user, Person person) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE persons SET birthday = ?, height = ?," +
                "passportID = ?" +
                "WHERE id = ? AND creator_id = ?"
        );

        if (person.getBirthday() == null) statement.setNull(1, Types.DATE);
        else statement.setDate(1, Date.valueOf(person.getBirthday()));

        statement.setFloat(2, person.getHeight());

        statement.setString(3, person.getPassportID());

        statement.setInt(4, person.getId());
        statement.setInt(5, user.getId());

        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    public int updateWorker(User user, Worker worker) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE workers SET name = ?, x = ?, y = ?, salary = ?, " +
                "pos = ?::pos, status = ?::status, person_id = ? " +
                "WHERE id = ? AND creator_id = ?"
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

        updatePerson(user, worker.getPerson());
        statement.setInt(7, worker.getPerson().getId());

        statement.setInt(8, worker.getId());
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


    public void dropTables() throws SQLException {
        String query = "BEGIN;\n" +
                       "\n" +
                       "DROP TABLE IF EXISTS workers;\n" +
                       "DROP TABLE IF EXISTS persons;\n" +
                       "DROP TABLE IF EXISTS users;\n" +
                       "\n" +
                       "DROP TYPE IF EXISTS status;\n" +
                       "DROP TYPE IF EXISTS pos;\n" +
                       "\n" +
                       "END;";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        System.out.println("Таблицы удалены");
    }

    public void createTables() throws SQLException {
        String query = """
                BEGIN;

                CREATE TYPE status AS ENUM (
                    'FIRED', 'RECOMMENDED_FOR_PROMOTION', 'REGULAR', 'PROBATION'
                    );

                CREATE TYPE pos AS ENUM (
                    'MANAGER', 'HUMAN_RESOURCES', 'HEAD_OF_DEPARTMENT', 'LEAD_DEVELOPER', 'BAKER'
                    );

                CREATE TABLE IF NOT EXISTS users
                (
                    id              SERIAL PRIMARY KEY,
                    name            VARCHAR(40) UNIQUE NOT NULL,
                    password_digest VARCHAR(96)        NOT NULL,
                    salt            VARCHAR(10)        NOT NULL
                );

                CREATE TABLE IF NOT EXISTS persons
                (
                    id         SERIAL PRIMARY KEY,
                    birthday   DATE,
                    height     FLOAT NOT NULL
                        CONSTRAINT positive_height CHECK (height > 0),
                    passportID TEXT
                        CONSTRAINT length CHECK (length(passportID) >= 7),
                    creator_id INT   NOT NULL REFERENCES users (id) ON DELETE CASCADE
                );

                CREATE TABLE IF NOT EXISTS workers
                (
                    id            SERIAL PRIMARY KEY,
                    name          TEXT               NOT NULL
                        CONSTRAINT not_empty_name CHECK (length(name) > 0),
                    x             INTEGER            NOT NULL,
                    y             INTEGER            NOT NULL,
                    creation_date TIMESTAMP DEFAULT NOW() NOT NULL,
                    salary        FLOAT
                        CONSTRAINT positive_salary CHECK (salary > 0),
                    pos      pos,
                    status        status,
                    person_id     INT UNIQUE REFERENCES persons (id) ON DELETE CASCADE,
                    creator_id    INT                NOT NULL REFERENCES users (id) ON DELETE CASCADE
                );

                END;
                """;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        System.out.println("Таблицы созданы");
    }
}
