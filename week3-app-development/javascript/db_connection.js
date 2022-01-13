const cassandra = require('cassandra-driver');
const TimeUuid = require('cassandra-driver').types.TimeUuid;

// This is the Zip file you downloaded
const SECURE_CONNECT_BUNDLE = '/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip'
// This is the "Client Id" value you obtained earlier
const USERNAME = ""
// This is the "Client Secret" value you obtained earlier
const PASSWORD = ""
// This is the keyspace name
const KEYSPACE = "todos"; 

// Init the connection and return the client
function init_connection(){
    var connection = {}
    connection.client = new cassandra.Client({ 
        cloud: { secureConnectBundle: SECURE_CONNECT_BUNDLE },
        keyspace: KEYSPACE,
        credentials: { username: USERNAME, password: PASSWORD }
    });
    return connection
}

connection = init_connection()


module.exports = connection;
