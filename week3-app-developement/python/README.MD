## Python Todos

An implementation of a Todo's API using Flask and the Cassandra Python driver.

### Getting Started
- Create a keyspace named `sag_python_todos` in your Astra DB instance.
- Copy the `.env.example` file and rename it to `.env` and fill it in with the appropriate values from Astra DB.
- Place your secure connect bundle from Astra DB and put it in the python folder inside of - the project and rename it to `bundle.zip`
- Run the project with `flask run --port 8080`, or run the tests with `pytest` 