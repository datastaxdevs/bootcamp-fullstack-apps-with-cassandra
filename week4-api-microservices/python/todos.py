import os
import uuid
from dotenv import load_dotenv
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider
from cassandra.cqlengine import columns
from cassandra.cqlengine.models import Model
from cassandra.cqlengine.management import sync_table
from cassandra.cqlengine import connection

load_dotenv()

# setup Astra DB connection
cloud_config = {
    "secure_connect_bundle": os.environ.get("ASTRA_DB_BUNDLE_PATH")
}
auth_provider = PlainTextAuthProvider(
    "token", os.environ.get("ASTRA_DB_APPLICATION_TOKEN"))
cluster = Cluster(cloud=cloud_config, auth_provider=auth_provider)
db_session = cluster.connect()
db_session.default_timeout = 60
connection.register_connection("db_session", session=db_session, default=True)


class Todos(Model):
    __keyspace__ = os.environ.get("ASTRA_DB_KEYSPACE")
    user_id = columns.Text(primary_key=True, required=True)
    item_id = columns.UUID(primary_key=True, default=uuid.uuid4)
    title = columns.Text(required=True)
    completed = columns.Boolean(default=False)


sync_table(Todos)
