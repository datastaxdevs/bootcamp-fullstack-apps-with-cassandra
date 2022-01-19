# 🎓🔥 NodeJS - Back End App Dev with Cassandra 🔥🎓

![datamodel](../images/AppDevSplash.png?raw=true)

## NodeJS - Back End App Dev with Cassandra

![Javascript](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-javascript.png)

## Table of contents

We will be walking through code that establishes a connection to the Astra database and does CRUD (Create, Read, Update, Delete commands).

- 6.[Connect to Astra](#-step-6-run-unit-test-ex06_connect_to_astrajs)
- 6a. [Create the todoitems Table](#-step-6a-run-unit-test-ex6a_create_tablejs)
- 6b. [Insert values](#-step-6b-run-unit-test-ex6b_insert_todosjs)
- 6c. [Retrieve all rows](#-step-6c-run-unit-test-ex6c_get_all_todosjs)
- 6d. [Update a todo](#-step-6d-run-unit-test-ex6d_update_a_todojs)
- 6e. [Remove a todo](#-step-6e-run-unit-test-ex6e_remove_a_todojs)
- 6f. [Remove all todos](#-step-6f-run-unit-test-ex6f_remove_all_todosjs)


Make sure you're in the right sub-directory (`javascript`) by issuing the following command in the GitPod terminal window.

```bash
cd /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/javascript
```

Verify again you're in the `javascript` sub-directory using the following command

```bash
pwd
```
**👁️ Expected output**

```
/workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/javascript
```

#### ✅ Step 6a. Run unit test `Ex06_Connect_to_Astra.js`**

From the GitPod terminal window issue the following command

```bash
open connection.js
```

and notice the following lines have been updated with the appropriate values.


```javascript
// This is the "Client Id" value you obtained earlier
const USERNAME = "FXXXXXXXXXXl"; 
// This is the "Client Secret" value you obtained earlier
const PASSWORD = "FXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXdeOE.kio_.L981NQ.xq5HqXDB7s_FIJC.ssbLgbdz+G1IC0BCwIA_ZrwPrQNJWUiv26uZf2f4wo";
```

You can optionally issue the following command or notice the changes in the Gitpod explorer window. Hit space bar to proceed through the changes or hit <Ctrl-C> or `q` to exit the command.


```bash
git diff connection.js
```

You are ready and can now test the connection to Astra with the following command

```bash
node Ex06_Connect_to_Astra.js 
```
**👁️ Expected output**

```
========================================
Start exercise
========================================
Your are now connected to Astra 'cndb' at 'us-east1'
SUCCESS
```
Now that we're successfully able to establish the connection, take a moment to walk through how the connection has been established by opening the source code using the following command

```
gp open Ex06_Connect_to_Astra.js
```

#### ✅ Step 6a. Run unit test `Ex6a_Create_Table.js`

Let's create the table.

Take a moment to review the code with the following command

```bash
gp open Ex6a_Create_Table.js
```
After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.


```bash
node Ex6a_Create_Table.js
```

**6a. Test output**

```bash
========================================
Start exercise
SUCCESS
========================================
```

**6a. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

```cql
use todos;
DESCRIBE TABLE todoitems;
```

**6a. CQL output**

**👁️ Expected output**

```bash
CREATE TABLE todos.todoitems (
    user_id text,
    item_id timeuuid,
    completed boolean,
    title text,
    PRIMARY KEY (user_id, item_id)
) WITH CLUSTERING ORDER BY (item_id ASC)
    AND additional_write_policy = '99PERCENTILE'
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.UnifiedCompactionStrategy'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair = 'BLOCKING'
    AND speculative_retry = '99PERCENTILE';
```

#### ✅ Step 6b. Run unit test `Ex6b_Insert_Todos.js`

Let's insert some items.

Take a moment to review the code with the following command

```bash
gp open Ex6b_Insert_Todos.js
```
After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
node Ex6b_Insert_Todos.js
```

**6b. Test output**

**👁️ Expected output**

```bash
========================================
Start exercise
SUCCESS
========================================
```

**6b. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

```cql
select * from todoitems;
```

**6b. CQL Output**

**👁️ Expected output**

```cql

 user_id | item_id                              | completed | title
---------+--------------------------------------+-----------+---------------------
    mary | 33333333-5cff-11ec-be16-1fedb0dfd057 |      True | Attend the workshop
    john | 11111111-5cff-11ec-be16-1fedb0dfd057 |      True |        Walk the dog
    john | 22222222-5cff-11ec-be16-1fedb0dfd057 |     False | Have lunch tomorrow

(3 rows)

```

#### ✅ Step 6c. Run unit test `Ex6c_Get_All_Todos.js`

Let's get all the todos associated with an user.

Take a moment to review the code with the following command

```bash
gp open Ex6c_Get_All_Todos.js
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
node Ex6c_Get_All_Todos.js
```

**6c. Test output**

**👁️ Expected output**

```bash
========================================
Start exercise
Row {
  user_id: 'john',
  item_id: TimeUuid {
    buffer: <Buffer 22 22 22 22 5c ff 11 ec be 16 1f ed b0 df d0 57>
  },
  completed: false,
  title: 'Have lunch tomorrow'
}
SUCCESS
========================================
```

You can verify via the CQL shell or via the console as well as you did in previous step(s).

#### ✅ Step 6d. Run unit test `Ex6d_Update_A_Todo.js`

Let's update an item.

Take a moment to review the code with the following command

```bash
gp open Ex6d_Update_A_Todo.js
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
node Ex6d_Update_A_Todo.js
```

**6d. Test output**

**👁️ Expected output**

```bash
========================================
Start exercise
Row {
  'system.totimestamp(item_id)': 2021-12-14T16:59:00.072Z,
  completed: true,
  title: 'Have lunch tomorrow'
}
SUCCESS
========================================
```

**6d. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

```cql
select * from todoitems;
```

**6d. CQL Output**

**👁️ Expected output**

```cql

 user_id | item_id                              | completed | title
---------+--------------------------------------+-----------+---------------------
    mary | 33333333-5cff-11ec-be16-1fedb0dfd057 |      True | Attend the workshop
    john | 11111111-5cff-11ec-be16-1fedb0dfd057 |      True |        Walk the dog
    john | 22222222-5cff-11ec-be16-1fedb0dfd057 |      True | Have lunch tomorrow

(3 rows)
```

#### ✅ Step 6e. Run unit test `Ex6e_Remove_A_Todo.js`

Let's remove an item.

Take a moment to review the code with the following command

```bash
gp open Ex6e_Remove_A_Todo.js
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
node Ex6e_Remove_A_Todo.js
```

**6e. Test output**

```bash
========================================
Start exercise
Row {
  'system.totimestamp(item_id)': 2021-12-14T16:59:00.072Z,
  completed: true,
  title: 'Have lunch tomorrow'
}
SUCCESS
========================================
```

**6e. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been removed.

```cql
select * from todoitems;
```

**6e. CQL Output**

**👁️ Expected output**

```cql
 user_id | item_id                              | completed | title
---------+--------------------------------------+-----------+---------------------
    mary | 33333333-5cff-11ec-be16-1fedb0dfd057 |      True | Attend the workshop
    john | 22222222-5cff-11ec-be16-1fedb0dfd057 |      True | Have lunch tomorrow

(2 rows)
```

#### ✅ Step 6f. Run unit test `Ex6f_Remove_All_Todos.js`

Finally, let's remove all todos associated with an user.

Take a moment to review the code with the following command

```bash
gp open Ex6f_Remove_All_Todos.js
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
node Ex6f_Remove_All_Todos.js
```

**6f. Test output**

**👁️ Expected output**

```bash
========================================
Start exercise
SUCCESS
========================================
```

**6f. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

**👁️ Expected output** 

```cql
select * from todoitems;
```

**6f. CQL Output**

```cql
 user_id | item_id | completed | title
---------+---------+-----------+-------

(0 rows)
```


**Cleanup**

**Caution: You will lose all the data** if you run the following command in the CQL Console

```cql
DROP TABLE IF EXISTS todos.todoitems;
```

[🏠 Go back](../README.MD)