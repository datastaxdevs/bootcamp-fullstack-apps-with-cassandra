const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    await client.execute(`
        CREATE TABLE todoitems (
            user_id         TEXT,
            item_id         TIMEUUID,
            title           TEXT,
            completed       BOOLEAN,
            PRIMARY KEY ((user_id), item_id)
        ) WITH CLUSTERING ORDER BY (item_id ASC);`);
    console.log("SUCCESS");
  } catch (e) {
    console.log(e);
  }
  console.log("========================================");
  process.exit();
})();
