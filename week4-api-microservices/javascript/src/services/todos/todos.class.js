const { Service } = require('feathers-cassandra');

exports.Todos = class Todos extends Service {
  constructor(options) {
    const { Model, ...otherOptions } = options;

    super({
      ...otherOptions,
      model: Model
    });
  }
};
