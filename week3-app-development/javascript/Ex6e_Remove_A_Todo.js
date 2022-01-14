const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    await client.execute(
      "DELETE FROM todoitems WHERE user_id='john' AND item_id=11111111-5cff-11ec-be16-1fedb0dfd057;"
    );
    const result = await client.execute(
      "SELECT toTimestamp(item_id), completed, title FROM todoitems WHERE user_id = 'john';"
    );
    result.rows.forEach((row) => {
      console.log(row);
    });
    console.log("SUCCESS");
  } catch (e) {
    console.log(e);
  }
  console.log("========================================");
  process.exit();
})();
