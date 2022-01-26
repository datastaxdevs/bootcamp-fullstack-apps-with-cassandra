import pytest
import os
from todos import Todos, session, KEYSPACE


@pytest.mark.it("it should create a todo")
def test_create_todo():
    todo = Todos.create(user_id="jake", title="hello world")
    assert todo.title == "hello world"


@pytest.mark.it("it should delete a single todo")
def test_delete_todo():
    todo = Todos.create(user_id="jake", title="hello world 2")
    todo.delete()
    try:
        todo = Todos.get(user_id="jake", item_id=todo.item_id)
        assert False
    except:
        assert True


@pytest.mark.it("it should update a single todo")
def test_update_todo():
    todo = Todos.create(user_id="jake", title="hello world 3")
    todo.update(title="hello world 4")
    assert todo.title == "hello world 4"


@pytest.mark.it("it should delete all todos")
def test_delete_all_todos():
    session.execute(
        f"TRUNCATE TABLE {KEYSPACE}.todoitems")
