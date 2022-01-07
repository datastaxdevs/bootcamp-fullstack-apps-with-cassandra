const getTodos = async () => {
  const response = await fetch("/todos");
  const todos = await response.json();
  return todos.length ? todos : [];
};

const createTodo = async (todo) => {
  const response = await fetch("/todos", {
    body: JSON.stringify(todo),
    method: "POST",
  });
  return response.json();
};

const updateTodo = async (todo) => {
  const id = todo.id;
  delete todo.id;
  const response = await fetch(`/todos/${id}`, {
    body: JSON.stringify(todo),
    method: "PATCH",
  });
  return response.json();
};

const deleteTodo = async (id) => {
  const response = await fetch(`/todos/${id}`, {
    body: JSON.stringify({ id }),
    method: "DELETE",
  });
  return response.json();
};

export default {
  getTodos,
  createTodo,
  deleteTodo,
  updateTodo,
};
