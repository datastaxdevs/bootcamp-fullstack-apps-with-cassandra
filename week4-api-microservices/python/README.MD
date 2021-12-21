## Django Todo API - Quick Start
* [Signup for DataStax Astra](https://astra.datastax.com), or login to your already existing account. 
* [Create an Astra DB Database](https://github.com/DataStax-Examples/sample-app-template/blob/master/GETTING_STARTED.md#create-an-astra-db) if you don't already have one.
* [Create an Astra DB Keyspace](https://github.com/DataStax-Examples/sample-app-template/blob/master/GETTING_STARTED.md#create-an-astra-db-keyspace) called `sag_python_todos` in your database.
* [Generate an Application Token](https://github.com/DataStax-Examples/sample-app-template/blob/master/GETTING_STARTED.md#create-an-application-token) with the role of `Database Administrator` for the Organization that your Astra DB is in.
* Copy the `.env.example` file to a file named `.env` and fill the required values in from your Application Token and [Astra DB connection settings](https://github.com/DataStax-Examples/sample-app-template/blob/master/GETTING_STARTED.md#get-your-astra-db-connection-settings). (The Django setting `SECRET_KEY` is unrelated to Astra and can be any alphanumeric string.)
* Get your [secure connect bundle](https://github.com/DataStax-Examples/sample-app-template/blob/master/GETTING_STARTED.md#get-an-astra-db-secure-connect-bundle) from the connect page of your database and save it to your `python` project folder. Rename it to `bundle.zip`.
* Install the python dependencies by running this command in the console:
```bash
pip install -r requirements.txt
```
* Create the project tables in Astra DB by running this command in the console:
```bash
python manage.py syncdb
```
* Run the Django project by running this command in the console:
```bash
python manage.py runserver
```