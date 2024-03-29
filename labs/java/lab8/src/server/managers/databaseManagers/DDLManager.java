package server.managers.databaseManagers;

import common.consoles.Console;
import common.consoles.StandardConsole;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс для работы с ddl-запросами по типу создания таблиц и удаления таблиц.
 */
public class DDLManager {
    private final ConnectionManager connectionManager;
    Console console = new StandardConsole();

    public DDLManager(String url, String login, String password) {
        connectionManager = new ConnectionManager(url, login, password);
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }
    public void dropTables() throws SQLException {
        String query = """
                BEGIN;

                DROP TABLE IF EXISTS workers;
                DROP TABLE IF EXISTS persons;
                DROP TABLE IF EXISTS users;
                DROP TABLE IF EXISTS commands;

                DROP TYPE IF EXISTS status;
                DROP TYPE IF EXISTS pos;

                END;
                """;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        console.write("Таблицы удалены");
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
                    name            VARCHAR(40) UNIQUE      NOT NULL,
                    password_digest VARCHAR(96)             NOT NULL,
                    salt            VARCHAR(10)             NOT NULL,
                    role            VARCHAR(25)             NOT NULL,
                    creation_date   TIMESTAMP DEFAULT NOW() NOT NULL
                );
                                
                CREATE TABLE IF NOT EXISTS commands
                (
                    id            SERIAL PRIMARY KEY,
                    name          VARCHAR(40) UNIQUE NOT NULL,
                    min_user_role VARCHAR(40) NOT NULL
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
                    name          TEXT                    NOT NULL
                        CONSTRAINT not_empty_name CHECK (length(name) > 0),
                    x             INTEGER                 NOT NULL,
                    y             INTEGER                 NOT NULL,
                    creation_date TIMESTAMP DEFAULT NOW() NOT NULL,
                    salary        FLOAT
                        CONSTRAINT positive_salary CHECK (salary > 0),
                    pos           pos,
                    status        status,
                    person_id     INT UNIQUE REFERENCES persons (id) ON DELETE CASCADE,
                    creator_id    INT                     NOT NULL REFERENCES users (id) ON DELETE CASCADE
                );
                                
                END;""";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        connection.close();
        console.write("Таблицы созданы");
    }
}
