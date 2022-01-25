require("dotenv").config();
const cassandra = require("cassandra-driver");

let client = null;
let todoMapper = null;

process.on("exit", () => client.shutdown());

module.exports = {
  client,
  todoMapper,
  init: async () => {
    client = new cassandra.Client({
      cloud: {
        secureConnectBundle: process.env.ASTRA_DB_BUNDLE_PATH,
      },
      credentials: {
        username: process.env.ASTRA_DB_CLIENT_ID,
        password: process.env.ASTRA_DB_CLIENT_SECRET,
      },
      keyspace: process.env.ASTRA_DB_KEYSPACE,
    });
    await client.connect();
    await client.execute(`CREATE TABLE IF NOT EXISTS todoitems (
          user_id         TEXT,
          item_id         TIMEUUID,
          title           TEXT,
          completed       BOOLEAN,
          offset          INT,
          PRIMARY KEY ((user_id), item_id)
      ) WITH CLUSTERING ORDER BY (item_id ASC);`);

    const mapper = new cassandra.mapping.Mapper(client, {
      models: {
        Todos: {
          tables: ["todoitems"],
          keyspace: process.env.ASTRA_DB_KEYSPACE,
        },
      },
    });
    todoMapper = mapper.forModel("Todos");
  },
  getTodos: async (userId) => {
    const res = await todoMapper.find({ user_id: userId });
    if (res.length) {
      return res
        .toArray()
        .map((item) => ({ ...item, item_id: item.item_id.toString() }));
    }
    return [];
  },
  deleteTodos: async () => {
    await client.execute("TRUNCATE TABLE todoitems");
    return [];
  },
  createTodo: async (todo) => {
    const newTodo = {
      item_id: cassandra.types.TimeUuid.now().toString(),
      ...todo,
    };
    await todoMapper.insert(newTodo);
    return newTodo;
  },
  updateTodo: async (userId, itemId, todo) => {
    const updatedTodo = {
      user_id: userId,
      item_id: itemId,
      ...todo,
    };
    await todoMapper.update(updatedTodo);
    return updatedTodo;
  },
  deleteTodo: async (userId, itemId) => {
    await todoMapper.remove({ user_id: userId, item_id: itemId });
    return {
      userId,
      itemId,
    };
  },
  getTodo: async (userId, itemId) => {
    const res = await todoMapper.get({ user_id: userId, item_id: itemId });
    if (res) {
      return { ...res, item_id: res.item_id.toString() };
    }
    return null;
  },
};
