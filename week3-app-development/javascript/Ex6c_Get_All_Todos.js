const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    const result = await client.execute(
      "SELECT * FROM todoitems WHERE user_id = 'john';"
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
