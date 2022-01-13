const connection = require('./db_connection')
const Uuid       = require('cassandra-driver').types.Uuid;

console.log("========================================")
console.log("Start exercise")

connection.client
.execute('SELECT * FROM system.local')
.then(function(result) {
	result.rows.forEach(row => {
        console.log("Your are now connected to Astra '%s' at '%s'", row.cluster_name, row.data_center)
    })
    connection.client.shutdown()
    console.log("SUCCESS")
})
.catch(function(error){
    console.log(error.message)
    connection.client.shutdown()
});

console.log("========================================")
