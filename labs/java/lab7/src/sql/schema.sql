BEGIN;

CREATE TYPE status AS ENUM (
    'FIRED', 'RECOMMENDED_FOR_PROMOTION', 'REGULAR', 'PROBATION'
    );

CREATE TYPE pos AS ENUM (
    'MANAGER', 'HUMAN_RESOURCES', 'HEAD_OF_DEPARTMENT', 'LEAD_DEVELOPER', 'BAKER'
    );

CREATE TYPE user_role AS ENUM (
    'USER_MIN', 'USER_MIDDLE', 'USER_SENIOR', 'ADMIN'
    );

CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(40) UNIQUE      NOT NULL,
    password_digest VARCHAR(96)             NOT NULL,
    salt            VARCHAR(10)             NOT NULL,
    role            user_role             NOT NULL,
    creation_date   TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS commands
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(40) UNIQUE NOT NULL,
    min_user_role user_role NOT NULL
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

END;