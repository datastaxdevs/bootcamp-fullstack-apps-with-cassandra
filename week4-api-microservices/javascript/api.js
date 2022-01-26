const express = require("express");
const app = express();
const cors = require("cors");
const url = require("url");
const todos = require("./todos");

const port = 8080;

app.use(express.json());
app.use(cors());
app.use(morgan("dev"));

app.get("/api/v1/:userId/todos", async (req, res) => {
  const todoRes = await todos.getTodos(req.params.userId);
  res.json(todoRes);
});

app.get("/api/v1/:userId/todos/:itemId", async (req, res) => {
  const todoRes = await todos.getTodo(req.params.userId, req.params.itemId);
  res.json(todoRes);
});

app.post("/api/v1/:userId/todos", async (req, res) => {
  const todoRes = await todos.createTodo({
    ...req.body,
    url: `https://${req.hostname}${req.originalUrl}`,
    user_id: req.params.userId,
  });
  res.json(todoRes);
});

app.patch("/api/v1/:userId/todos/:itemId", async (req, res) => {
  const todoRes = await todos.updateTodo(req.params.userId, req.params.itemId, {
    ...req.body,
  });
  res.json(todoRes);
});

app.delete("/api/v1/:userId/todos/:itemId", async (req, res) => {
  const todoRes = await todos.deleteTodo(req.params.userId, req.params.itemId);
  res.json(todoRes);
});

app.delete("/api/v1/:userId/todos", async (req, res) => {
  const todoRes = await todos.deleteTodos();
  res.json(todoRes);
});

app.listen(port, async () => {
  await todos.init();
  console.log(`Example app listening on port ${port}`);
});

module.exports = app;
