import React, { Component } from "react";
import classnames from "classnames";

export default class TodoTextInput extends Component {
  state = {
    title: this.props.title || "",
  };

  handleSubmit = (e) => {
    const title = e.target.value.trim();
    if (e.which === 13) {
      this.props.onSave(title);
      if (this.props.newTodo) {
        this.setState({ title: "" });
      }
    }
  };

  handleChange = (e) => this.setState({ title: e.target.value });

  handleBlur = (e) => {
    if (!this.props.newTodo) {
      this.props.onSave(e.target.value);
    }
  };

  render() {
    return (
      <input
        className={classnames({
          edit: this.props.editing,
          "new-todo": this.props.newTodo,
        })}
        type="text"
        placeholder={this.props.placeholder}
        autoFocus="true"
        value={this.state.title}
        onBlur={this.handleBlur}
        onChange={this.handleChange}
        onKeyDown={this.handleSubmit}
      />
    );
  }
}
