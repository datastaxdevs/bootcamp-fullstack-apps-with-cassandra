CREATE TABLE todoitems (
    user_id         TEXT,
    item_id         UUID,
    title           TEXT,
    completed       BOOLEAN,
    PRIMARY KEY ((user_id), item_id)
);