#!/usr/bin/env python3
# THIS FILE WILL BE OVERWRITTEN. DO NOT MAKE ANY CHANGES HERE

import atexit
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

# This is the Zip file you downloaded
SECURE_CONNECT_BUNDLE = '/workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip'
# This is the "Client Id" value you obtained earlier
USERNAME = "FXHtFByhHAjqNjdcLgZNYEll"
# This is the "Client Secret" value you obtained earlier
PASSWORD = "F,h1w,Z4PGdiqBYwWMseRks_BZzZNdYWC__yNDj_F_fOfNYn3MjSsdeOE.kio_.L981NQ.xq5HqXDB7s_FIJC.ssbLgbdz+G1IC0BCwIA_ZrwPrQNJWUiv26uZf2f4wo"
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
