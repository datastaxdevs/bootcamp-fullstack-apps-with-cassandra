// THIS FILE WILL BE OVERWRITTEN. DO NOT MAKE ANY CHANGES
const cassandra = require("cassandra-driver");

// This is the Zip file you downloaded
const SECURE_CONNECT_BUNDLE =
  "/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip";
// This is the "Client Id" value you obtained earlier
const USERNAME = "SUuZbUWjxJNmUHkpiXpOHxpK";
// This is the "Client Secret" value you obtained earlier
const PASSWORD = "zM1PancBS._JXYA-ekONq4Nr_7QN81nhiLYf5H+6cyiA2T+R,cKc5sYkT-j,dCoLF+R.5.,2ft+qOn2KPwZZGWh-jMpPXl7eX0ksrGpNLYX1_J8wRLQgwalq4Sl+UYdz";
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
