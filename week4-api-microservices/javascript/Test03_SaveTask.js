const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    await client.execute(
      "INSERT INTO todoitems (user_id, item_id, completed, title, offset) VALUES ( 'john', 11111111-5cff-11ec-be16-1fedb0dfd057, true, 'Walk the dog', 0);"
    );

    console.log("SUCCESS");
  } catch (e) {
    console.log(e);
  }
  console.log("========================================");
  process.exit();
})();
