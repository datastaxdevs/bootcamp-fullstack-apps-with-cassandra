const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    await client.execute(`DROP TABLE IF EXISTS todoitems ;`);
    await client.execute(`
        CREATE TABLE IF NOT EXISTS todoitems (
            user_id         TEXT,
            item_id         TIMEUUID,
            title           TEXT,
            url             TEXT,
            completed       BOOLEAN,
            offset          INT,
            PRIMARY KEY ((user_id), item_id)
        ) WITH CLUSTERING ORDER BY (item_id ASC);`);
    console.log("SUCCESS");
  } catch (e) {
    console.log(e);
  }
  console.log("========================================");
  process.exit();
})();
