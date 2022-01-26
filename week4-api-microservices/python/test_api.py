import pytest
import uuid
from api import app

USER_ID = "jake"

with app.test_client() as client:
    @pytest.mark.it("it should get an empty list of todos")
    def test_get_empty_todos():
        get_res = client.get(f"/api/v1/{USER_ID}/todos")
        get_res_json = get_res.get_json()
        assert len(get_res_json) == 0

    @pytest.mark.it("it should create a todo")
    def test_create_todo():
        res = client.post(f"/api/v1/{USER_ID}/todos",
                          json={"title": "hello world"})
        res_json = res.get_json()
        assert res_json["title"] == "hello world"

    @pytest.mark.it("it should not create a todo")
    def test_not_create_todo():
        res = client.post(f"/api/v1/{USER_ID}/todos", json={"title": ""})
        assert res.status_code == 400

    @pytest.mark.it("it should get a single todo")
    def test_get_todo():
        res = client.post(f"/api/v1/{USER_ID}/todos",
                          json={"title": "hello world 2"})
        res_json = res.get_json()
        get_res = client.get(f"/api/v1/{USER_ID}/todos/{res_json['item_id']}")
        get_res_json = get_res.get_json()
        assert get_res_json["title"] == "hello world 2"

    @pytest.mark.it("it should not get a single todo")
    def test_not_get_todo():
        get_res = client.get(f"/api/v1/{USER_ID}/todos/{uuid.uuid1()}")
        assert get_res.status_code == 404

    @pytest.mark.it("it should get a list of todos")
    def test_get_todos():
        get_res = client.get(f"/api/v1/{USER_ID}/todos")
        get_res_json = get_res.get_json()
        assert len(get_res_json) == 2

    @pytest.mark.it("it should delete a single todo")
    def test_delete_todo():
        res = client.post(f"/api/v1/{USER_ID}/todos",
                          json={"title": "hello world 3"})
        res_json = res.get_json()
        delete_res = client.delete(
            f"/api/v1/{USER_ID}/todos/{res_json['item_id']}")
        delete_res_json = delete_res.get_json()
        assert delete_res_json["title"] == "hello world 3"
        get_res = client.get(f"/api/v1/{USER_ID}/todos/{res_json['item_id']}")
        assert get_res.status_code == 404
        del_res = client.delete(
            f"/api/v1/{USER_ID}/todos/{res_json['item_id']}")
        assert del_res.status_code == 404

    @pytest.mark.it("it should update a single todo")
    def test_update_todo():
        res = client.post(f"/api/v1/{USER_ID}/todos",
                          json={"title": "hello world 4"})
        res_json = res.get_json()
        patch_res = client.patch(
            f"/api/v1/{USER_ID}/todos/{res_json['item_id']}", json={"completed": True})
        assert patch_res.status_code == 200
        get_res = client.get(f"/api/v1/{USER_ID}/todos/{res_json['item_id']}")
        get_res_json = get_res.get_json()
        assert get_res_json["completed"] == True
        bad_res = client.patch(
            f"/api/v1/{USER_ID}/todos/{res_json['item_id']}", json={"title": ""})
        assert bad_res.status_code == 400
        missing_res = client.patch(f"/api/v1/{USER_ID}/todos/{uuid.uuid1()}")
        assert missing_res.status_code == 404

    @pytest.mark.it("it should delete all todos")
    def test_delete_all_todos():
        delete_res = client.delete(f"/api/v1/{USER_ID}/todos")
        delete_res_json = delete_res.get_json()
        assert delete_res_json["success"] == True
        get_res = client.get(f"/api/v1/{USER_ID}/todos")
        get_res_json = get_res.get_json()
        assert get_res_json == []
