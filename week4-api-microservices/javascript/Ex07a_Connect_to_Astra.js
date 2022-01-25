const { client } = require("./connection");

console.log("========================================");
console.log("Start exercise");

(async () => {
  try {
    const result = await client.execute("SELECT * FROM system.local");
    result.rows.forEach((row) => {
      console.log(
        "Your are now connected to Astra '%s' at '%s'",
        row.cluster_name,
        row.data_center
      );
    });
    console.log("SUCCESS");
  } catch (e) {
    console.log(e);
  }
  console.log("========================================");
  process.exit();
})();
