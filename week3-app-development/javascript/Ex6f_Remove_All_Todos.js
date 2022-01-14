const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    await client.execute("TRUNCATE TABLE todoitems;");
    console.log("SUCCESS");
  } catch (e) {
    console.log(e);
  }
  console.log("========================================");
  process.exit();
})();
