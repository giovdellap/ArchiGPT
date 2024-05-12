CREATE TABLE member (
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(100) PRIMARY KEY,
    password CHAR(256) NOT NULL
);
INSERT INTO member (name, surname, birth_date, email, username, password) VALUES ('admin', 'admin', '2000-01-01', 'admin@example.com', 'admin', '9331a1d273d1c186ac996050b184dd0c616d495c4b6ff7bc9ba016c21cd331ea');
CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description TEXT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    status VARCHAR(50) DEFAULT 'not_completed' CHECK (status IN ('completed','not_completed')),
    member VARCHAR REFERENCES member(username),
    type VARCHAR(10) DEFAULT 'personal' CHECK (type IN ('personal', 'event')),
    duration INTEGER DEFAULT 60 CHECK (duration > 0)
);
CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    content VARCHAR(500),
    type VARCHAR(50) NOT NULL DEFAULT 'message',
    read boolean NOT NULL DEFAULT FALSE,
    username VARCHAR(100),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CHECK ( 
        type = 'invite'
        OR type = 'message'
        OR type = 'survey'
        OR type = 'event'
        OR type = 'admin'
        OR type = 'modifyevent'
    )
);
CREATE TABLE team (
    id SERIAL UNIQUE,
    name VARCHAR(100) NOT NULL DEFAULT 'unnamed_team',
    description VARCHAR(500),
    color VARCHAR(100),
    CONSTRAINT joinn_pkey
        PRIMARY KEY(name, description)
);
CREATE TABLE joinTeam (
    username VARCHAR(100),
    team INT,
    CONSTRAINT join_pkey
        PRIMARY KEY(username, team),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE
);
CREATE TABLE manage (
    admin VARCHAR(100),
    team INT,
    CONSTRAINT manage_pkey
        PRIMARY KEY(admin, team),
    CONSTRAINT fk_admin
        FOREIGN KEY(admin)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE
);
CREATE TABLE invite (
    username VARCHAR(100),
    admin VARCHAR(100) NOT NULL,
    team INT,
    CONSTRAINT invite_pkey
        PRIMARY KEY(username, team),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_admin_team
        FOREIGN KEY(admin, team)
            REFERENCES manage(admin, team)
            ON DELETE CASCADE
);
CREATE TABLE includes (
    event INT,
    team INT,
    username VARCHAR(100),
    state VARCHAR(50) NOT NULL,
    CONSTRAINT includes_pkey
        PRIMARY KEY(event, team, username),
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_event
        FOREIGN KEY(event)
            REFERENCES task(id)
            ON DELETE CASCADE,
    CHECK (
        state = 'accepted'
        OR state = 'rejected'
        OR state = 'pending'
    )
);
CREATE TABLE survey (
    id SERIAL PRIMARY KEY,
    text VARCHAR(500) NOT NULL,
    due_date DATE NOT NULL
);
CREATE TABLE sended_by (
    admin VARCHAR(100) NOT NULL,
    team INT NOT NULL,
    survey INT PRIMARY KEY,
    CONSTRAINT fk_survey
        FOREIGN KEY(survey)
            REFERENCES survey(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_admin_team
        FOREIGN KEY(admin, team)
            REFERENCES manage(admin, team)
            ON DELETE CASCADE
);
CREATE TABLE option (
    survey INT NOT NULL,
    id SERIAL PRIMARY KEY,
    text VARCHAR(500) NOT NULL,
    counter INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_survey
        FOREIGN KEY(survey)
            REFERENCES survey(id)
            ON DELETE CASCADE
);
CREATE TABLE vote (
    option INT,
    username VARCHAR(100),
    CONSTRAINT pk_vote
        PRIMARY KEY(option, username),
    CONSTRAINT fk_option
        FOREIGN KEY(option)
            REFERENCES option(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE
);
CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    datetime TIMESTAMP NOT NULL,
    content TEXT NOT NULL,
    sender VARCHAR(100) NOT NULL,
    team INT NOT NULL,
    CONSTRAINT fk_team
        FOREIGN KEY(team)
            REFERENCES team(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_sender
        FOREIGN KEY(sender)
            REFERENCES member(username)
            ON DELETE CASCADE
);
CREATE TABLE add (
    personal_task INT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES member(username)
            ON DELETE CASCADE,
    CONSTRAINT fk_task
        FOREIGN KEY(personal_task)
            REFERENCES task(id)
            ON DELETE CASCADE
);