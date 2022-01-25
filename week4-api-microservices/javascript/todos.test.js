const assert = require("assert");
const todos = require("./todos");

describe("Todos", () => {
  it("it should initialize", async () => {
    await todos.init();
    assert.equal(true, true);
  });

  it("it should create a todo", async () => {
    const todo = await todos.createTodo({
      user_id: "jake",
      title: "hello world",
    });
    assert.equal(todo.title, "hello world");
  });

  it("it should get todos", async () => {
    const res = await todos.getTodos("jake");
    assert.equal(res.length, 1);
  });

  it("it should update todos", async () => {
    const res = await todos.getTodos("jake");
    const existingTodo = res[0];
    await todos.updateTodo("jake", existingTodo.item_id, {
      title: "hello world 2",
    });
    const todo = await todos.getTodo("jake", existingTodo.item_id);
    assert.equal(todo.title, "hello world 2");
  });

  it("it should delete a todo", async () => {
    const res = await todos.getTodos("jake");
    const existingTodo = res[0];
    await todos.deleteTodo("jake", existingTodo.item_id);
    const todo = await todos.getTodo("jake", existingTodo.item_id);
    assert.equal(todo, null);
  });

  it("it should delete todos", async () => {
    await todos.deleteTodos();
    assert.equal(true, true);
  });
});
