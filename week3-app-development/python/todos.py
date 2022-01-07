import os
import uuid
from dotenv import load_dotenv
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider
from cassandra.cqlengine import columns, ValidationError
from cassandra.cqlengine.models import Model
from cassandra.cqlengine.management import sync_table
from cassandra.cqlengine import connection
from flask import Flask, jsonify, request, send_from_directory

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
    id = columns.UUID(primary_key=True, default=uuid.uuid4)
    title = columns.Text(required=True)
    order = columns.Integer()
    completed = columns.Boolean(default=False)


sync_table(Todos)


# setup flask
app = Flask(__name__, static_folder="../ui/build")


@app.route("/", defaults={"path": ""})
@app.route("/<path:path>")
def serve(path):
    if path != "" and os.path.exists(app.static_folder + "/" + path):
        return send_from_directory(app.static_folder, path)
    else:
        return send_from_directory(app.static_folder, "index.html")


@app.route("/todos", methods=["GET"])
def get_todos():
    return jsonify([dict(x) for x in Todos.all()])


@app.route("/todos", methods=["DELETE"])
def delete_todos():
    db_session.execute(
        f"DROP TABLE {os.environ.get('ASTRA_DB_KEYSPACE')}.todos")
    sync_table(Todos)
    return jsonify({"success": True})


@app.route("/todos", methods=["POST"])
def create_todo():
    try:
        request_json = request.get_json(force=True)
        new_todo = Todos.create(**request_json)
        return jsonify(dict(new_todo))
    except ValidationError as e:
        return jsonify({"error": str(e)}), 400


@app.route("/todos/<todo_id>", methods=["GET"])
def get_todo(todo_id):
    try:
        todo = Todos.get(id=todo_id)
        return jsonify(dict(todo))
    except Model.DoesNotExist:
        return jsonify({"error": "not found"}), 404


@app.route("/todos/<todo_id>", methods=["DELETE"])
def delete_todo(todo_id):
    try:
        todo = Todos.get(id=todo_id)
        todo.delete()
        return jsonify(dict(todo))
    except Model.DoesNotExist:
        return jsonify({"error": "not found"}), 404


@app.route("/todos/<todo_id>", methods=["PATCH"])
def update_todo(todo_id):
    try:
        request_json = request.get_json(force=True)
        todo = Todos.get(id=todo_id)
        todo.update(**request_json)
        return jsonify(dict(todo))
    except Model.DoesNotExist:
        return jsonify({"error": "not found"}), 404
    except ValidationError as e:
        return jsonify({"error": str(e)}), 400
