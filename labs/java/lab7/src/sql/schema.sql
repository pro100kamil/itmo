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
    passportID TEXT  NOT NULL
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
    creation_date DATE DEFAULT NOW() NOT NULL,
    salary        FLOAT             NOT NULL
        CONSTRAINT positive_salary CHECK (salary > 0),
    pos      pos,
    status        status,
    person_id     INT REFERENCES persons (id) ON DELETE CASCADE,
    creator_id    INT                NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

END;