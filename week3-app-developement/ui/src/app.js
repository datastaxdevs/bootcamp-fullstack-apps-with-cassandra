import React, { Component } from "react";
import Header from "./Header";
import TodoList from "./TodoList";
import api from "./utils/api";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      todos: [],
      gameID: null,
      player: 0,
    };
  }

  addTodo = (title) => {
    api
      .createTodo({
        completed: false,
        title,
      })
      .then((todo) => {
        api.getTodos().then((todos) => this.setState({ todos }));
      });
  };

  deleteTodo = (id) => {
    api.deleteTodo(id).then((todo) => {
      api.getTodos().then((todos) => this.setState({ todos }));
    });
  };

  editTodo = (id, title, completed) => {
    api
      .updateTodo({
        id,
        title,
        completed,
      })
      .then((todo) => {
        api.getTodos().then((todos) => this.setState({ todos }));
      });
  };

  completeTodo = (id, title, completed) => {
    api
      .updateTodo({
        id,
        title,
        completed: !completed,
      })
      .then((todo) => {
        api.getTodos().then((todos) => this.setState({ todos }));
      });
  };

  componentDidMount() {
    api.getTodos().then((todos) => this.setState({ todos }));
  }

  actions = {
    addTodo: this.addTodo,
    deleteTodo: this.deleteTodo,
    editTodo: this.editTodo,
    completeTodo: this.completeTodo,
    completeAll: this.completeAll,
    clearCompleted: this.clearCompleted,
  };

  render() {
    return (
      <div>
        <Header addTodo={this.actions.addTodo} />
        <TodoList todos={this.state.todos} actions={this.actions} />
      </div>
    );
  }
}

export default App;
