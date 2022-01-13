#!/usr/bin/env python3      
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

# This is the Zip file you downloaded
SECURE_CONNECT_BUNDLE = '/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip'
# This is the "Client Id" value you obtained earlier
USERNAME = ""
# This is the "Client Secret" value you obtained earlier
PASSWORD = ""
# This is the keyspace name
KEYSPACE = "todos"; 

class Connection:
    def __init__(self):
        self.secure_connect_bundle=SECURE_CONNECT_BUNDLE
        self.path_to_creds=''
        self.cluster = Cluster(
            cloud={
                'secure_connect_bundle': self.secure_connect_bundle
            },
            auth_provider=PlainTextAuthProvider(USERNAME, PASSWORD)
        )
        self.session = self.cluster.connect(KEYSPACE)
    def close(self):
        self.cluster.shutdown()
        self.session.shutdown()
