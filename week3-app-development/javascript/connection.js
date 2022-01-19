// WARNING: THIS FILE IS GOING TO BE OVERWRITTEN
const cassandra = require("cassandra-driver");

// This is the Zip file you downloaded
const SECURE_CONNECT_BUNDLE =
  "/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip";
// This is the "Client Id" value you obtained earlier
const USERNAME = "";
// This is the "Client Secret" value you obtained earlier
const PASSWORD = "";
// This is the keyspace name
const KEYSPACE = "todos";

const client = new cassandra.Client({
  cloud: { secureConnectBundle: SECURE_CONNECT_BUNDLE },
  keyspace: KEYSPACE,
  credentials: { username: USERNAME, password: PASSWORD },
});

process.on("exit", () => client.shutdown());

module.exports = {
  client,
};
