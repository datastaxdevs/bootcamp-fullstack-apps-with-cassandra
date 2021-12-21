import uuid
from datetime import datetime
from cassandra.cqlengine import columns
from django_cassandra_engine.models import DjangoCassandraModel


class TodoItem(DjangoCassandraModel):
    id = columns.UUID(primary_key=True, default=uuid.uuid4)
    title = columns.Text(max_length=256, required=True)
    completed = columns.Boolean(required=True, default=False)
    url = columns.Text(max_length=256, required=True)
    order = columns.Integer()
