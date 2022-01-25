## Javascript Todos

An implementation of a Todo's API using ExpressJS and the Cassandra Javascript driver.

### Getting Started

- Create a keyspace named `sag_todos` in your Astra DB instance.
- Copy the `.env.example` file and rename it to `.env` and fill it in with the appropriate values from Astra DB.
- Place your secure connect bundle from Astra DB and put it in the javascript folder inside of - the project and rename it to `bundle.zip`
- Run the project with `npm start`, or run the tests with `npm test`
