CREATE TABLE todoitems (
    user_id         TEXT,
    item_id         TIMEUUID,
    title           TEXT,
    completed       BOOLEAN,
    PRIMARY KEY ((user_id), item_id)
) WITH CLUSTERING ORDER BY (item_id ASC);
