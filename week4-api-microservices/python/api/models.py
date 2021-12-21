import uuid
from datetime import datetime
from cassandra.cqlengine import columns
from django_cassandra_engine.models import DjangoCassandraModel


class TodoItem(DjangoCassandraModel):
    id = columns.UUID(primary_key=True, default=uuid.uuid4)
    text = columns.Text(required=True)
    order = columns.Integer(null=True, blank=True)
    completed = columns.Boolean(null=True, blank=True, default=False)
    created_at = columns.DateTime(default=datetime.now)
