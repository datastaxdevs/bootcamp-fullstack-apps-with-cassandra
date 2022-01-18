## 🎓🔥 Back End App Dev with Cassandra 🔥🎓

![datamodel](images/AppDevSplash.png?raw=true)

If you did not attend the previous weeks' workshop **STOP - Complete Week 2 - [Data Modelling](../week2-data-modelling) first.**

Welcome to the 'App Dev of back end with Cassandra' workshop! In this two-hour workshop, the Developer Advocate team of DataStax presents the process and the basic tenets of backend App Dev. If you did not attend the previous workshops, please look at the previous weeks of the bootcamp 
[Week 1 - Intro to Cassandra](../week1-intro-to-cassandra) and [Week 2 - Data Modelling](../week2-data-modelling).

For the hands-on part of this workshop, we will use Astra DB, a Database-as-a-Service
built on Cassandra and delivered by DataStax.

It doesn't matter if you join our workshop live or you prefer to do at your own pace, we have you covered. But please if you did not attend [Week 1 - Intro to Cassandra](../week1-intro-to-cassandra) and/or [Week 2 - Data Modelling](../week2-data-modelling) we suggest you get caught up since the material in this workshop will build on top of the previous weeks' workshops!

In this repository, you'll find everything you need for this workshop:

- [Materials used during presentations](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/blob/main/week3-app-development/slides/WEEK3%20-%20App%20Development%20with%20Cassandra.pdf)
- [Hands-on exercises](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra/tree/main/week3-app-development#table-of-contents)
- [Workshop video](https://youtu.be/PGOv68epSQ8)
- [Discord chat](https://dtsx.io/discord)
- [Questions and Answers](https://community.datastax.com/)

## Homework

To complete the workshop and get a verified badge, follow these simple steps:

1. Watch the workshop live or recorded. Pick a language and run through the exercises.
2. **Write a new exercise/test**: Transfer/Copy all Todoitems from any one user_id  to a new user_id with completed being false. At the very least one Todoitem should be created.
3. [Submit the Homework through this form](https://dtsx.io/homework-bootcamp-2022-week3) and attach the screenshots above.
4. Give us a few days to review your submission and relax: your well-earned badge will soon land in your mailbox!

## 0. Table of Contents

| Title  | Description
|---|---|
| **Slide deck** | [Slide deck for the workshop](slides/WEEK3%20-%20App%20Development%20with%20Cassandra.pdf) |
| **1. Connect to your Astra DB instance** | [Connect to your Astra DB instance](#1-connect-to-your-astra-db-instance) |
| **2. Create Astra Token** | [Create Astra Token](#2-create-astra-token) |
| **3. Launch Gitpod** | [Launch Gitpod](#3-launch-gitpod) |
| **4. Know your Gitpod** | [Know your Gitpod](#4-know-your-gitpod) |
| **5. Setup your Application** | [Setup for the Applications](#5-setup-for-the-applications)
| **6-6f. CRUD with Cassandra** | [CRUD with Cassandra](#6-crud-with-cassandra) |
| **7. Badges** | [Badges](#7-badges) |


## 1. Connect to your Astra DB instance

**Once again, If you did not attend the previous workshops, please look at the previous weeks of the bootcamp 
[Week 1 - Intro to Cassandra](../week1-intro-to-cassandra) and [Week 2 - Data Modelling](../week2-data-modelling) please complete that first.**

Verify that the keyspace `todos` indeed exists in database `workshops` as shown in the [astra dashboard](https://astra.datastax.com).

![Astra](images/Verifytodos.png?raw=true)

Now that you've created a database and keyspace already and familiarized yourself with the Astra console, we will start preparing to connect to the database, programmatically.

There are several language drivers (includes Java, NodeJS, Python) to access a Cassandra database programmatically and each with different capabilites. The driver matrix is at [https://docs.datastax.com/en/driver-matrix/doc/driver_matrix/common/driverMatrix.html](https://docs.datastax.com/en/driver-matrix/doc/driver_matrix/common/driverMatrix.html).

In principle, they all work by setting up a connection to the database and manipulating the data via CQL statements or higher abstractions referred to as collections.

We will start by establishing connectivity (which is common across languages) and then branch off into the respective language paths.

To establish connectivity to the Astra database that we created, we need some credentials from the Astra console and we need to use a secure connect bundle alongside the driver. The next few steps will accomplish just that.

## 2. Create Astra Token

We need to create a **token** that we will use as our credentials.

✅ **Step 2a: Generate Token**

Following the [Manage Application Tokens docs](https://docs.datastax.com/en/astra/docs/manage-application-tokens.html) create a token with `Database Administrator` roles.

- Go the `Organization Settings`

- Go to `Token Management`

- Pick the role `Database Admnistrator` on the select box

- Click Generate token

**👁️ Walkthrough**

![image](images/astra-create-token.gif?raw=true)

This is what the token page looks like. You can now download the values as a CSV. We will need those values but you can also keep this window open for use later.

![image](images/astra-token.png?raw=true)

Notice the clipboard icon at the end of each value.

- `Client Id:` We will use it as a _username_ to connect to Astra.

- `Client Secret:` We will use it as a _password_ to connect to Astra.

- `appToken:` It can be used as an api token Key to interact with APIs. **(we won't be using this in today's workshop)**

To know more about roles, tokens, etc. you can look at [this video.](https://www.youtube.com/watch?v=TUTCLsBuUd4)

**Note: Make sure you don't close the window accidentally or otherwise - if you close this window before you copy the values, the application token is lost forever. They won't be available later for security reasons. You'll have to create a new application token**

We are now set with the database and credentials. Let's start coding!

[🏠 Back to Table of Contents](#0-table-of-contents)

## 3. Launch Gitpod

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

[🏠 Back to Table of Contents](#table-of-contents)

## 4. Know your Gitpod

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

[🏠 Back to Table of Contents](#0-table-of-contents)

## 5. Setup for the applications

To run the application(s) you need to provide the database credentials.

✅ **Step 5a: Note the two values from the downloaded token**


Note down the values of `Client Id` and `Client Secret` from values noted earlier that we will subsequently use for `username` and `password` in the respective language in a later step.

✅ **Step 5b: Download the secure connect bundle**

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
ls -l secure-connect-workshops.zip
```

The file size should be roughly 12K otherwise something may have gone wrong in the process.

**👁️ Expected output**

```
-rw-r--r-- 1 gitpod gitpod 12231 Oct 26 00:15 secure-connect-workshops.zip
```

TADA your application is configured we can finally play with some code.

[🏠 Back to Table of Contents](#0-table-of-contents)

✅ **Step 5c: Setup the username and password**

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

Issue the following command from the Gitpod terminal window if you're interested in knowing what happened behind the scenes/script.

```bash
git diff
```

**A BIG NOTE OF CAUTION: It's not a good practice to include username, password and other secrets in the source file. Don't ever git commit with clear case passwords, etc. This is done purely for convenience of getting started.**

✅ **Step 5d: Verify cql access from Gitpod**

Invoke `zsh` as shown below (far right top corner of Gitpod terminal window).

![dotenv](images/dotenv2.png?raw=true)

If you completed the earlier steps properly, you should be dropped into the CQL shell as shown below.

![dotenv](images/dotenv3.png?raw=true)

Run the following command from the CQL shell of the Gitpod terminal window.

```
describe keyspaces;
```

**👁️ Expected output**

```
system_virtual_schema  system_auth   datastax_sla        todos
todolist               system_views  data_endpoint_auth
system_schema          system        system_traces     

FXHtFByhHAjqNjdcLgZNYEll@cqlsh> 
```


We will refer to this as the `CQL shell` (`python2`) and the original shell as the `Gitpod terminal` (`bash ..`). You can click on those tabs to switch between the shells.

![dotenv](images/dotenv4.png?raw=true)

TADA! Now you're really done done with the setup and ready to start coding.

## 6. CRUD with Cassandra

Exercises will be different for each language. Pick the language you feel the most confortable with with and JUMP to the respective README.

| Sections | Material Description
| - |---|
| **![JAVA](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-java.png)** | [Exercises 6 ... with Java](./java/README.MD) |
| **![JAVASCRIPT](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-javascript.png)** | [Exercises 6 ...  with Javascript](./javascript/README.MD) |
| **![PYTHON](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-python.png)** | [Exercises 6 ... with Python](./python/README.MD) |

[🏠 Back to Table of Contents](#0-table-of-contents)

## 7. Badges

Refer back to the [Homework](#Homework) which is required to earn the badges.

<img src="images/appdev_badge.png?raw=true" width="200" align="right" />


**Now go ahead and start app dev: Good luck!**

> You might be eligible for a badge when you attend more workshops and complete the requirements.

<img src="images/bootcamp_badge.png?raw=true" width="200" align="left" />

> Sincerely yours, The DataStax Developers
> THE END.

[🏠Back to HOME workshop](https://github.com/datastaxdevs/bootcamp-fullstack-apps-with-cassandra)



