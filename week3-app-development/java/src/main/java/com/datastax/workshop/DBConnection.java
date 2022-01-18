package com.datastax.workshop;

/*
 *
 * TODO Please change this constants with the values
 * you used when you created the ASTRA instance.
 */
public interface DBConnection {
    
    // This is the Zip file you downloaded
    String SECURE_CONNECT_BUNDLE = "/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip";

    // This is the "Client Id" value you obtained earlier
    String USERNAME = "$ASTRA_USERNAME";

    // This is the "Client Secret" value you obtained earlier
    String PASSWORD = "$ASTRA_PASSWORD";
    
    // This is the keyspace name, recommended value was killrvideo
    String KEYSPACE = "todos";
}
