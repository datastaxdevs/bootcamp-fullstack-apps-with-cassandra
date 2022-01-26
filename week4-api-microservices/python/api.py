import uuid
from dotenv import load_dotenv
from cassandra.cqlengine.management import sync_table
from cassandra.cqlengine import ValidationError
from cassandra.cqlengine.models import Model
from flask import Flask, jsonify, request
from todos import Todos, session, KEYSPACE
from flask_cors import CORS

load_dotenv()


# setup flask
app = Flask(__name__)
CORS(app, resources={r"/api/*": {"origins": "*"}})


@app.route("/api/v1/<user_id>/todos", methods=["GET"])
def get_todos(user_id):
    res = [dict(x) for x in Todos.filter(user_id=user_id)]
    return jsonify(res)


@app.route("/api/v1/<user_id>/todos", methods=["DELETE"])
def delete_todos(user_id):
    session.execute(
        f"TRUNCATE TABLE {KEYSPACE}.todoitems")
    sync_table(Todos)
    return jsonify({"success": True})


@app.route("/api/v1/<user_id>/todos", methods=["POST"])
def create_todo(user_id):
    try:
        request_json = request.get_json(force=True)
        item_id = uuid.uuid1()
        request_json["item_id"] = item_id
        request_json["url"] = f"https://{request.headers.get('X-Forwarded-Host', 'localhost')}/api/v1/{user_id}/todos/{item_id}"
        new_todo = Todos.create(user_id=user_id, **request_json)
        return jsonify(dict(new_todo))
    except ValidationError as e:
        return jsonify({"error": str(e)}), 400


@app.route("/api/v1/<user_id>/todos/<item_id>", methods=["GET"])
def get_todo(user_id, item_id):
    try:
        todo = Todos.get(user_id=user_id, item_id=item_id)
        return jsonify(dict(todo))
    except Model.DoesNotExist:
        return jsonify({"error": "not found"}), 404


@app.route("/api/v1/<user_id>/todos/<item_id>", methods=["DELETE"])
def delete_todo(user_id, item_id):
    try:
        todo = Todos.get(user_id=user_id, item_id=item_id)
        todo.delete()
        return jsonify(dict(todo))
    except Model.DoesNotExist:
        return jsonify({"error": "not found"}), 404


@app.route("/api/v1/<user_id>/todos/<item_id>", methods=["PATCH"])
def update_todo(user_id, item_id):
    try:
        todo = Todos.get(user_id=user_id, item_id=item_id)
        request_json = request.get_json(force=True)
        todo.update(**request_json)
        return jsonify(dict(todo))
    except Model.DoesNotExist:
        return jsonify({"error": "not found"}), 404
    except ValidationError as e:
        return jsonify({"error": str(e)}), 400
