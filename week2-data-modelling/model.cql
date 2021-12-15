

CREATE TABLE IF NOT EXISTS todos (
    uid         uuid,
    title       text,
    completed   boolean,
    offset      int,
    PRIMARY KEY((uid))
);