## 🎓🔥 Build a Data Model with Cassandra 🔥🎓

![datamodel](images/datamodel.png?raw=true)

Welcome to the 'Building Efficient Data Model with Cassandra' workshop! In this two-hour workshop, the Developer Advocate team of DataStax presents the process and the main tenets
of Data Modeling as is done when working with Apache Cassandra, the powerful distributed
NoSQL database that has been covered in [the first week of this bootcamp](../week1-intro-to-cassandra).

For the hands-on part of this workshop, we will use Astra DB, a Database-as-a-Service
built on Cassandra and delivered by DataStax, to create the actual data model which
will then be used in the next weeks as the data store for a Web application.

It doesn't matter if you join our workshop live or you prefer to do at your own pace, we have you covered. But please if you did not attend [the previous week](../week1-intro-to-cassandra)
we suggest you start from that one!
In this repository, you'll find everything you need for this workshop:

- [Materials used during presentations](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/raw/main/week2-data-modelling/slides/WEEK1%20-%20Introduction%20to%20Apache%20Cassandra.pdf)
- [Hands-on exercises](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra//tree/main/week2-data-modelling#table-of-contents)
- [Workshop video](https://youtu.be/2g1DPHMmI8s)
- [Discord chat](https://dtsx.io/discord)
- [Questions and Answers](https://community.datastax.com/)

## Homework

To complete the workshop and get a verified badge, follow these simple steps:

1. Watch the workshop live or recorded.
2. Try to come up with your own data model (in the form of Chebotko diagrams or CQL `CREATE TABLE` statements) for the case described [below](#5-data-model-assignment) and get ready to submit it in the form of a screenshot.
3. Complete the DS201 (Cassandra Fundamentals) course sections 9 - 16 [HERE](https://academy.datastax.com). Look at the setup instructions from [Week 1](../week1-intro-to-cassandra) if needed.

> _If you are feeling adventurous complete the DS201 exercises using the following Docker image. DO NOT attempt to use the virtual machines in the course._
>
> ```
> docker run -d -t --name class-201 drchung5/ds201
> docker exec -u root -it class-201 bash
> ```
4. [Submit the Homework through this form](https://dtsx.io/homework-cassandra-data-modelling) and attach the screenshots above.
5. Give us a few days to review your submission, and relax: your well-earned badge will soon land in your mailbox!

## Table of Contents

| Title  | Description
|---|---|
| **Slide deck** | [Slide deck for the workshop](slides/Presentation.pdf) |
| **1. Create your Astra DB instance** | [Create your Astra DB instance](#1-create-your-astra-db-instance) |
| **2. A first data model** | [A first data model](#2-a-first-data-model) |
| **3. Another example** | [Another example](#3-another-example) |
| **4. A simple TODO App** | [A simple TODO App](#4-a-simple-todo-app) |
| **5. Data model assignment** | [Data model assignment](#5-data-model-assignment) |


## 1. Create your Astra DB instance

**NOTE**: if you come from Week 1, you will have your Astra DB account already: in that case, all you have to do
is creating a new keyspace, called `todos`, then skip to [Section 2](#2-a-first-data-model). If you stick to this keyspace name, it will be ready to be used when building the application in the next weeks!

<details><summary>Show me how!</summary>
  <img src="https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/raw/main/week2-data-modelling/images/create_keyspace_todos.gif?raw=true" />
</details>

If you are new to Astra DB, please read on.

_**`ASTRA DB`** is the simplest way to run Cassandra with zero operations at all - just push the button and get your cluster. No credit card required, $25.00 USD credit every month, roughly 5M writes, 30M reads, 40GB storage monthly - sufficient to run small production workloads._

✅ Register (if needed) and Sign In to Astra DB [https://astra.datastax.com](https://astra.dev/1-12): You can use your `Github`, `Google` accounts or register with an `email`.

_Make sure to chose a password with minimum 8 characters, containing upper and lowercase letters, at least one number and special character_

✅ Choose "Start Free Now"

Choose the "Start Free Now" plan, then "Get Started" to work in the free tier.

You will have plenty of free initial credit (renewed each month!), roughly corresponding
to 80 GB of storage and 20M read/write operations.

> If this is not enough for you, congratulations! You are most likely running a mid- to large-sized business! In that case you should switch to a paid plan.

(You can follow this [guide](https://docs.datastax.com/en/astra/docs/creating-your-astra-database.html) to set up your free-tier database with the $25 monthly credit.)

- **For the database name** - use `workshops`. While Astra DB allows you to fill in these fields with values of your own choosing, please follow our recommendations to ensure the application runs properly.

- **For the keyspace name** - use `todos`. Please stick to this name, it will make the following steps much easier (you have to customize here and there otherwise). In short:

| Parameter | Value 
|---|---|
| Database name | workshops  |
| Keyspace name | todos |

_Note_: if you already have a `workshops` database, for instance from a previous workshop with us, you can simply create the keyspace with the `Add Keyspace` button in your Astra DB dashboard: the new keyspace will be available in few seconds.

- **For provider and region**: Choose any provider (either GCP, AWS or Azure). Region is where your database will reside physically (choose one close to you or your users).

- **Create the database**. Review all the fields to make sure they are as shown, and click the `Create Database` button.

You will see your new database as `Pending` in the Dashboard;
the status will change to `Active` when the database is ready. This will only take 2-3 minutes
(you will also receive an email when it is ready).

## 2. A first data model

todo (2. A first data model).

## 3. Another example

todo (3. Another example).

## 4. A simple TODO App

todo (4. A simple TODO App).

## 5. Data model assignment

todo (5. Data model assignment).

