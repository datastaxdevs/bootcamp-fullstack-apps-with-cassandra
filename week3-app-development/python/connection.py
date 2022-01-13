#!/usr/bin/env python3

import atexit
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

# This is the Zip file you downloaded
SECURE_CONNECT_BUNDLE = '/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip'
# This is the "Client Id" value you obtained earlier
USERNAME = ""
# This is the "Client Secret" value you obtained earlier
PASSWORD = ""
# This is the keyspace name
KEYSPACE = "todos"


secure_connect_bundle = SECURE_CONNECT_BUNDLE
path_to_creds = ''
cluster = Cluster(
    cloud={
        'secure_connect_bundle': SECURE_CONNECT_BUNDLE
    },
    auth_provider=PlainTextAuthProvider(USERNAME, PASSWORD)
)
session = cluster.connect(KEYSPACE)


@atexit.register
def shutdown_driver():
    cluster.shutdown()
    session.shutdown()
