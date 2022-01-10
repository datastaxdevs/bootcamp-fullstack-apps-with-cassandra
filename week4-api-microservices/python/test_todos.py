import pytest
import os
from todos import Todos, db_session
from cassandra.cqlengine.models import Model


@pytest.mark.it("it should create a todo")
def test_create_todo():
    todo = Todos.create(title="hello world")
    assert todo.title == "hello world"


@pytest.mark.it("it should delete a single todo")
def test_delete_todo():
    todo = Todos.create(title="hello world 2")
    todo.delete()
    try:
        todo = Todos.get(id=todo.id)
        assert False
    except Model.DoesNotExist:
        assert True


@pytest.mark.it("it should update a single todo")
def test_update_todo():
    todo = Todos.create(title="hello world 3")
    todo.update(title="hello world 4")
    assert todo.title == "hello world 4"


@pytest.mark.it("it should delete all todos")
def test_delete_all_todos():
    db_session.execute(
        f"DROP TABLE {os.environ.get('ASTRA_DB_KEYSPACE')}.todos")
