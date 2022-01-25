# 🎓🔥 API and Microservices with Apache Cassandra 🔥🎓

Welcome to the *Build API and Microservices with Apache Cassandra* workshop! In this two-hour workshop, the Developer Advocate team of DataStax presents the process and the basic tenets of building a REST API (which is the foundation of microservices) with data backed by Apache Cassandra.

<img src="images/splash.png?raw=true"  width="600px"/>

## 🎓 BootCamp 2022

This session is part of the 4-week bootcamp for you to learn anything you need to 
develop great applications for Apache Cassandra. Each Session can be followed as 
**standalone** and this one is no different. It is strongly recommended that you complete weeks 1,2,3
to get the most of this week.

- [Week 1 - Intro to Cassandra](../week1-intro-to-cassandra)
- [Week 2 - Data Modelling](../week2-data-modelling)
- [Week 3 - Application Developement](../week3-app-development)

## 📋 Materials

It doesn't matter if you join our workshop live or you prefer to do at your own pace, we have you covered.

For the hands-on part of this workshop, we will use **[Astra DB](http://astra.dev/1-26)**, a Database-as-a-Service
built on Cassandra and delivered by DataStax.

In this repository, you'll find everything you need for this workshop:

- [Materials used during presentations](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/blob/main/week4-api-microservices/slides/slides.pdf)
- [Hands-on exercises](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/tree/main/week4-api-microservices#-Hands-on)
- [Workshop video (*Recording in progress*)](#)
- [Discord chat](https://dtsx.io/discord)
- [Questions and Answers](https://community.datastax.com/)

## 📋 Hands-On

<img src="images/splash.png?raw=true" align="right" width="400px"/>

1. [Connect to your Astra DB instance](#1-connect-to-your-astra-db-instance)
2. [Create Astra Token](#2-create-astra-token)
3. [Run the TodoApplication](#3-run-the-todoapplication)
4. [Launch Gitpod](#4-launch-gitpod)
5. [Know you Gitpod](#5-know-your-gitpod)
6. [Rest Api with Apache Cassandra](#6-setup-for-the-applications)
7. [Setup for the applications](#7-rest-api-with-cassandra)
8. [Badges and Homework](#8-badges-and-homework)

## 1. Connect to your Astra DB instance

**✨ Please visit [https://astra.datastax.com](https://astra.dev/1-26) to create your free Astra DB instance, required for today's hands-on exercises! 🔥** More details [here](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/tree/main/week2-data-modelling#1-create-your-astra-db-instance) on how to proceed.

### `Option 1A` (You already have an Astra Account)

If you attended previous weeks, you will have your Astra DB account already.

> ✅ If your database has the status `Standby` then it needs to be resumed before going on.

<details><summary>Show me how to resume my database.</summary>
  <img src="images/resume-db.png?raw=true" />
</details>

> ✅ Next, make sure you have a keyspace, called `todos`, if not please add it. then skip to [Section 2](#2-create-astra-token).

<details><summary>Show me how to add a keyspace!</summary>
  <img src="https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/raw/main/week2-data-modelling/images/create_keyspace_todos.gif?raw=true" />
</details>

### `Option 1B` (You are new to Astra)

If you are new to Astra DB, please read on.

_**`ASTRA DB`** is the simplest way to run Cassandra with zero operations at all - just push the button and get your cluster. No credit card required, $25.00 USD credit every month, roughly 5M writes, 30M reads, 40GB storage monthly - sufficient to run small production workloads._

#### ✅ Register (if needed) and Sign In to Astra DB [https://astra.datastax.com](https://astra.dev/1-25): You can use your `Github`, `Google` accounts or register with an `email`.

_Make sure to chose a password with minimum 8 characters, containing upper and lowercase letters, at least one number and special character_

#### ✅ Choose "Start Free Now"

Choose the "Start Free Now" plan, then "Get Started" to work in the free tier.

You will have plenty of free initial credit (renewed each month!), roughly corresponding
to 80 GB of storage and 20M read/write operations.

> If this is not enough for you, congratulations! You are most likely running a mid- to large-sized business! In that case you should switch to a paid plan.

(You can follow this [guide](https://docs.datastax.com/en/astra/docs/creating-your-astra-database.html) to set up your free-tier database with the $25 monthly credit.)

#### ✅ Fill the form

- **For the database name** - use `workshops`. While Astra DB allows you to fill in these fields with values of your own choosing, please follow our recommendations to ensure the application runs properly.

- **For the keyspace name** - use `todos`. Please stick to this name, it will make the following steps much easier (you have to customize here and there otherwise). In short:

| Parameter       | Value
|-----------------|---|
| *Database name* | `workshops`  |
| *Keyspace name* | `todos` |

- **For provider and region**: Choose any provider (either GCP, AWS or Azure). Region is where your database will reside physically (choose one close to you or your users).

- **Create the database**. Review all the fields to make sure they are as shown, and click the `Create Database` button.

You will see your new database as `Pending` in the Dashboard; the status will change to `Active` when the database is ready. This will only take 2-3 minutes
(you will also receive an email when it is ready).

[🏠 Back to Table of Contents](#-hands-on)

## 2. Create Astra Token

We need to create a **token** that we will use as our credentials. The token do not expired so 
if you already have one you can reuse it.

✅ **Step 2a: Generate Token**

Following the [Manage Application Tokens docs](https://docs.datastax.com/en/astra/docs/manage-application-tokens.html) create a token with `Database Administrator` roles.

- Go the `Organization Settings`

- Go to `Token Management`

- Pick the role `Database Admnistrator` on the select box

- Click Generate token

**👁️ Walkthrough**

![image](images/astra-create-token.gif?raw=true)

This is what the token page looks like. You can now download the values as a CSV since you will need those values very soon.

![image](images/astra-token.png?raw=true)

Notice the clipboard icon at the end of each value.

- `Client Id:` We will use it as a _username_ to connect to Astra.

- `Client Secret:` We will use it as a _password_ to connect to Astra.

- `appToken:` It can be used as an api token Key to interact with APIs. **(we won't be using this in today's workshop)**

To know more about roles, tokens, etc. you can look at [this video.](https://www.youtube.com/watch?v=TUTCLsBuUd4)

**Note: Make sure you don't close the window accidentally or otherwise - if you close this window before you copy the values, the application token is lost forever. They won't be available later for security reasons. You'll have to create a new application token**

We are now set with the database and credentials. Let's get started!

[🏠 Back to Table of Contents](#-hands-on)

## 3. Run the TodoApplication

**TodoMVC** is a fabulous community contribution that helps developers compare frameworks on the basis of actual project code, not just claims and anecdotes. TodoMVC is an immensely valuable attempt at a difficult problem - providing a structured way of comparing JS libraries and frameworks.

![TodoMVC](images/todomvc.png?raw=true)

**✅ Step 1a. Visit [TodoMVC.com](http://todomvc.com/) to discover what we are building today** : We want you to visualize the application (if you're building a brand new app you might create wire frame diagrams, for example) before we start coding. We already have a working application -so please browse [http://todomvc.com/](http://todomvc.com/) and play around. You will see that there are multiple implementations, feel free to pick one of those and test it live in your browser.


**[Todo-Backend](https://www.todobackend.com/)** is an example to showcase backend tech stacks. The Todo-Backend project defines a simple web API spec for managing a todo list. Contributors implement that spec using various tech stacks. Those implementations are cataloged below. A spec runner verifies that each contribution implements the exact same API, by running an automated test suite which defines the API.

![TodoBackend](images/todobackend.png?raw=true)

There are multiple free implementations available. Some implementations provide endpoints to be tested in `heroku`. [Heroku provides free hosting](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku). With `Heroku` The application will be inactive until you access the endpoint. Due to this it will take 30s (or more) for the first person hitting the endpoint to wake up the application. Among the available free endpoints we can recommend:
- `#1` : [https://todo-back-springboot220-java12.herokuapp.com/todos](https://todo-back-springboot220-java12.herokuapp.com/todo)
- `#2` : [https://todo-quarkus.herokuapp.com/todos](https://todo-quarkus.herokuapp.com/todos)
- `#3` : [https://todo-backend-micronaut.herokuapp.com/todos](https://todo-backend-micronaut.herokuapp.com/todos)

**✅ Step 1b. Test TodoBackEnd Spec Runner** : Locate the [spec runner](https://www.todobackend.com/specs/index.html) and use one of the `endpoints` listed above to evaluate if API matches the requirements. To do so copy-paste one URL and click `Run Tests`.

![TodoBackendTest](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend-runtest.png?raw=true)

**Pick any one of the three listed below:**

- [`SpecRunner#1`](https://www.todobackend.com/specs/index.html?https://todo-back-springboot220-java12.herokuapp.com/todos)

- [`SpecRunner#2`](https://www.todobackend.com/specs/index.html?https://todo-quarkus.herokuapp.com/todos)

- [`SpecRunner#3`](https://www.todobackend.com/specs/index.html?https://todo-backend-micronaut.herokuapp.com/todos)

**👁️ Expected output**

![TodoBackendOuput](images/todobackend-output-host.png?raw=true)

**Note:** *During the live stream there will be dozens of people testing the same endpoints.  It is possible that we could hit some slowness or issues with race conditions.*

**✅ Step 1c. Test TodoBackEnd Web Client** : todoBackend.com provides a [client](https://www.todobackend.com/client/index.html) to work with the API. As before pick one of the `endpoints` listed before and try the client.

![TodoBackendClient](images/todobackend-runclient.png?raw=true)

**Pick one of the three from below:**

- [`WebClient#1`](https://www.todobackend.com/client/index.html?https://todo-back-springboot220-java12.herokuapp.com/todos)

- [`WebClient#2`](https://www.todobackend.com/client/index.html?https://todo-quarkus.herokuapp.com/todos)

- [`WebClient#3`](https://www.todobackend.com/client/index.html?https://todo-backend-micronaut.herokuapp.com/todos)

**👁️ Expected output**

![TodoBackendClient](https://github.com/DataStax-Academy/workshop-spring-data-cassandra/blob/main/images/todobackend-output-client.png?raw=true)

Our mission for the next hour is to implement the *backend API* and store data into Apache Cassandra™. We have you covered by providing the skeleton of the application.

[🏠 Back to Table of Contents](#-hands-on)


## 4. Launch Gitpod

[Gitpod](https://www.gitpod.io/) is an IDE 100% online based on [VS Code](https://github.com/gitpod-io/vscode/blob/gp-code/LICENSE.txt?lang=en-US). To initialize your environment simply click on the button below _(CTRL + Click to open in new tab)_ You will be asked for your github account, as needed.

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra)

**👁️ Expected output**

_The screenshot may be slightly different based on your default skin and a few edits in the read me._

![gitpod](images/gitpod-01-home-plain.png?raw=true)

**That's it.** Gitpod provides all tools we will need today including for respective languages. 

**You may safely ignore the error output at the end of the terminal window.**

**👁️ Expected output**

![image](images/gitpod-bootcampappdev-1.jpg?raw=true)

Although GitPod terminal might seem to be available, the setup might still be ongoing. Wait for a few minutes before entering commands in the GitPod terminal window.

[🏠 Back to Table of Contents](#-hands-on)

## 5. Know your Gitpod

**You are not going to run any command in this step but it's a great idea to take a moment and familiarize yourself with Gitpod. These steps provides some details for just that. Take a tour around and get comfortable.**

Take a moment to read this entire section since it'll help you with the rest of the workshop as you'll be spending most of your time in Gitpod. If you're familiar with Gitpod, you can easily skip this entire section.

The extreme left side has the explorer view(1). The top left, middle to right is where you'll be editing files(2), etc. and the bottom left, middle to right is what we will refer to as the Gitpod terminal window(3) as shown below.

**👁️ Expected output**

![gitpod](images/gitpod-01-home-annotated.png?raw=true)


You can always get back to the file explorer view whenever by clicking on the hamburger menu on the top left followed by `View` and `Explorer` as shown below.

![gitpod](images/Filexplorer0.png?raw=true)

At the top right of the terminal window is a `+` sign which allows you to create multiple shells. Familiarize yourself since we will be using `cql` in one of the shells.

![gitpod](images/shells.png?raw=true)

You may encounter the following at different steps and although this may not be applicable right away, the steps are included **in advance** and summarized here so that you can keep an eye out for it. Different paths and different environments might be slightly different although Gipod levels the playing field a bit.

You can allow cutting and pasting into the window by clicking on `Allow` as shown below.

![gitpod](images/allow.png?raw=true)

Or allow ports to be opened by just exiting windows that are informational messages about ports like below.

![gitpod](images/OpenPorts.png?raw=true)

**OPTIONAL:** In Gitpod, If you prefer "Dark Mode" you can set it in [Preferences](https://gitpod.io/preferences) as shown below.

 ![gitpod prefs](images/gitpodpreferences1.png?raw=true)

[🏠 Back to Table of Contents](#-hands-on)

## 6. Setup for the applications

To run the application(s) you need to provide the database credentials.

####  ✅ **Step 6a: Note the two values from the downloaded token**


Note down the values of `Client Id` and `Client Secret` from values noted earlier that we will subsequently use for `username` and `password` in the respective language in a later step.

#### ✅ **Step 6b: Download the secure connect bundle**

This next step is probably the most involved step in the entire workshop. The goal of this step is to get the customized connect bundle into Gitpod. One of the several ways of doing this is as follows.

Start with the [Astra DB dashboard](https://astra.datastax.com) and for the database workshops,

1. Click on `Connect` tab.
2. Click on Connect using any of the drivers `Node.js` (`javascript`), `Python` or `Java`.
3. Click on `Download Bundle`.
4. Click on `Secure Connect Bundle` to be able to copy the link locally.

as shown below.

![gitpod](images/secureconnectbundle1.png?raw=true)	

Locate the file locally in the finder/explorer window. Drag and drop the file into the Gitpod explorer window (on the left side, making sure that the cursor, indicating the drop, is positioned in the Gitpod explorer window as shown below.

![gitpod](images/secureconnectbundle3.png?raw=true)

In the Gitpod terminal window, verify that you dropped the right file and at the top level directory

```bash
ls -l /workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip
```

If you get a message `cannot access` or the file size is not roughly 12K, something may have gone wrong in the process and you need to repeat this step (step 5b).

**👁️ Expected output**

```
-rw-r--r-- 1 gitpod gitpod 12261 Jan 12 23:34 /workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip
```

TADA your application is configured we can finally play with some code.

[🏠 Back to Table of Contents](#0-table-of-contents)

#### ✅ **Step 6c: Setup the username and password**

We have provided some convenience scripts to setup the credentials across all the languages.

As a first step `cd` to the `week3-app-development` sub-directory with the following command.

```bash
cd week3-app-development
```


Let's start by issuing the following command.

```bash
cp .env.sample .env
```

Open this file and update it by entering the `ASTRA_USERNAME` (`Client Id`) and `ASTRA_PASSWORD` (`Client Secret`) values. **Values are not shown here. You obtained this in an earlier step.** 

You may issue the following command in the GitPod terminal window.

```bash
gp open .env
```

![dotenv](images/dotenv1.png?raw=true)

You are almost ready to start coding. The next step is to run the convenience script which updates the connection files that you'll be using with the appropriate values. Run the following command

```bash
./plugastracreds
```

> **A BIG NOTE OF CAUTION: It's not a good practice to include username, password and other secrets in the source file. Don't ever git commit with clear case passwords, etc. This is done purely for convenience of getting started.**

✅ **Step 6d: Verify cql access from Gitpod**

Invoke `zsh` as shown below (far right top corner of Gitpod terminal window).

![dotenv](images/dotenv2.png?raw=true)

If you completed the earlier steps properly, you should be dropped into the CQL shell as shown below.

![dotenv](images/dotenv3.png?raw=true)

Run the following command from the CQL shell of the Gitpod terminal window.

```sql
describe keyspaces;
```

**👁️ Expected output**

```sql
system_virtual_schema  system_auth   datastax_sla        todos
todolist               system_views  data_endpoint_auth
system_schema          system        system_traces     

FXHtFByhHAjqNjdcLgZNYEll@cqlsh> 
```


We will refer to this as the `CQL shell` (`python2`) and the original shell as the `Gitpod terminal` (`bash ..`). You can click on those tabs to switch between the shells.

![dotenv](images/dotenv4.png?raw=true)

TADA! Now you're really done done with the setup and ready to start coding.

[🏠 Back to Table of Contents](#-hands-on)

## 7. REST API with Cassandra

Exercises will be different for each language. Pick the language you feel the most confortable with with and JUMP to the respective README.

| Sections | Material Description
| - |---|
| **![JAVA](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-java.png)** | [Exercises 7a..7g with Java](./java/README.MD) |
| **![JAVASCRIPT](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-javascript.png)** | [Exercises 7a..7g with Javascript](./javascript/README.MD) |
| **![PYTHON](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-python.png)** | [Exercises 7a..7g with Python](./python/README.MD) |

[🏠 Back to Table of Contents](#-hands-on)

## 8. Badges and Homework

> You might be eligible for these badges when you attend workshops and complete the requirements.

<img src="images/bootcamp_badge.png?raw=true" width="200" align="left" />

<img src="images/week4_badge.png?raw=true" width="200" align="right" />

To complete the workshop and get a verified badge, follow these simple steps:

1. Watch the workshop live or recorded. Pick a language and run through the exercises.
2. **Write a new exercise/test**: TODO
3. [Submit the Homework through this form](https://dtsx.io/homework-cassandra-api) and attach the screenshots above.
4. Give us a few days to review your submission and relax: your well-earned badge will soon land in your mailbox!


> Sincerely yours, The DataStax Developers
> THE END.


[🏠Back to HOME workshop](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra)

