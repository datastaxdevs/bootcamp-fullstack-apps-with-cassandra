# 🎓🔥 Java - Back End App Dev with Cassandra 🔥🎓

![datamodel](../images/AppDevSplash.png?raw=true)

## Java - Back End App Dev with Cassandra

![JAVA](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-java.png)

## Table of contents

We will be walking through code that establishes a connection to the Astra database and does CRUD (Create, Read, Update, Delete commands).

- 6.[Connect to Astra](#-step-6-run-unit-test-ex06_connect_to_astrajava)
- 6a. [Create the todoitems Table](#-step-6a-run-unit-test-ex6a_create_tablejava)
- 6b. [Insert values](#-step-6b-run-unit-test-ex6b_insert_todosjava)
- 6c. [Retrieve all rows](#-step-6c-run-unit-test-ex6c_get_all_todosjava)
- 6d. [Update a todo](#-step-6d-run-unit-test-ex6d_update_a_todojava)
- 6e. [Remove a todo](#-step-6e-run-unit-test-ex6e_remove_a_todojava)
- 6f. [Remove all todos](#-step-6f-run-unit-test-ex6f_remove_all_todosjava)

Make sure you're in the right sub-directory (`java`) by issuing the following command in the GitPod terminal window.

```bash
cd /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java
```

Verify again you're in the `java` sub-directory using the following command

```bash
pwd
```
**👁️ Expected output**

```
/workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java
```
**Suggestion**

Read, digest and modify the source code in the next few steps and get comfortable with the drivers and the code in preparation for the remaining workshop series.

#### ✅ Step 6. Run unit test `Ex06_Connect_to_Astra.java`

From the GitPod terminal window issue the following command 

```bash
gp open src/main/java/com/datastax/workshop/DBConnection.java
```
and notice the following lines have been updated with the appropriate values.

```java
// This is the "Client Id" value you obtained earlier
String USERNAME = "FXXXXXXXXXXl";
// This is the "Client Secret" value you obtained earlier
String PASSWORD = "FXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXdeOE.kio_.L981NQ.xq5HqXDB7s_FIJC.ssbLgbdz+G1IC0BCwIA_ZrwPrQNJWUiv26uZf2f4w";
```

You can optionally issue the following command or notice the changes in the explorer window. Hit space bar to proceed through the changes or hit <Ctrl-C> or `q` to exit the command.


```bash
git diff src/main/java/com/datastax/workshop/DBConnection.java
```

Compile the project with the following command

```bash
mvn clean compile
```

**👁️ Expected output**

```
[INFO] 
[INFO] ---------------------< com.datastax.workshop:crud >---------------------
[INFO] Building crud 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ crud ---
[INFO] Deleting /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ crud ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ crud ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java/target/classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.283 s
[INFO] Finished at: 2022-01-13T01:11:38Z
[INFO] ------------------------------------------------------------------------
```

You are ready and can now test the connection to Astra with the following command

```bash
mvn test -Dtest=com.datastax.workshop.Ex06_Connect_to_Astra
```

**👁️ Expected output**

```bash
[should_connect_to_astra] ========================================
[should_connect_to_astra] Start Exercise
[should_connect_to_astra] File '.../secure-connect-workshops.zip' located
[should_connect_to_astra] Connected with Keyspace todos
[should_connect_to_astra] [OK]
[should_connect_to_astra] ========================================
```

Now that we're successfully able to establish the connection, take a moment to walk through how the connection has been established by opening the source code using the following command

```
gp open src/main/java/com/datastax/workshop/DBConnection.java
```

#### ✅ Step 6a. Run unit test `Ex6a_Create_Table.java`

Let's create the table.

Take a moment to review the code with the following command

```bash
gp open src/test/java/com/datastax/workshop/Ex6a_Create_Table.java
```
After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.


```bash
mvn test -Dtest=com.datastax.workshop.Ex6a_Create_Table
```

**6a. Test output**

```bash
[should_create_a_table_1] ========================================
[should_create_a_table_1] Start exercise
[should_create_a_table_1] [OK]
[should_create_a_table_1] ========================================

[should_create_a_table_2] ========================================
[should_create_a_table_2] Start exercise
[should_create_a_table_2] [OK]
[should_create_a_table_2] ========================================
```

**6a. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

```cql
use todos;
DESCRIBE TABLE todoitems;
```

**6a. CQL output**

**👁️ Expected output**

```cql
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

#### ✅ Step 6b. Run unit test `Ex6b_Insert_Todos.java`

Let's insert some items.

Take a moment to review the code with the following command

```bash
gp open src/test/java/com/datastax/workshop/Ex6b_Insert_Todos.java
```
After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
mvn test -Dtest=com.datastax.workshop.Ex6b_Insert_Todos
```

**6b. Test output**

**👁️ Expected output**

```bash
[should_insert_todos_1] ========================================
[should_insert_todos_1] Start with static CQL
[should_insert_todos_1] Task '11111111-5cff-11ec-be16-1fedb0dfd057' has been inserted
[should_insert_todos_1] Task '22222222-5cff-11ec-be16-1fedb0dfd057' has been inserted
[should_insert_todos_1] Task '33333333-5cff-11ec-be16-1fedb0dfd057' has been inserted
[should_insert_todos_1] [OK]
[should_insert_todos_1] ========================================

[should_create_task_cql] ========================================
[should_create_task_cql] Start Exercise
[should_create_task_cql] [OK]
[should_create_task_cql] ========================================

[should_insert_task_simple_position] ========================================
[should_insert_task_simple_position] Start Exercise
[should_insert_task_simple_position] Task b496c5b0-7841-11ec-9a7f-f559f873bd1c has been inserted
[should_insert_task_simple_position] [OK]
[should_insert_task_simple_position] ========================================

[should_insert_task_simple_label] ========================================
[should_insert_task_simple_label] Start Exercise
[should_insert_task_simple_label] Task b7172f00-7841-11ec-9a7f-f559f873bd1c has been inserted
[should_insert_task_simple_label] [OK]
[should_insert_task_simple_label] ========================================

[should_insert_task_prepared] ========================================
[should_insert_task_prepared] Start Exercise
[should_insert_task_prepared] Task b9e458c0-7841-11ec-9a7f-f559f873bd1c has been inserted
[should_insert_task_prepared] [OK]
[should_insert_task_prepared] ========================================
```

**6b. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

```cql
select * from todoitems;
```

**6b. CQL Output**

**👁️ Expected output**

```cql

 user_id    | item_id                              | completed | title
------------+--------------------------------------+-----------+--------------------------------
 createTask | fe2f09c0-7838-11ec-8113-894c6aa07015 |     False |                       Task CQL
 createTask | 00d76d70-7839-11ec-8113-894c6aa07015 |     False | Task Simple Statement position
 createTask | 03519530-7839-11ec-8113-894c6aa07015 |     False |     Task Simple Statement name
 createTask | 05e5ad90-7839-11ec-8113-894c6aa07015 |     False |                  Prepared Task
       mary | 33333333-5cff-11ec-be16-1fedb0dfd057 |      True |            Attend the workshop
       john | 11111111-5cff-11ec-be16-1fedb0dfd057 |      True |                   Walk the dog
       john | 22222222-5cff-11ec-be16-1fedb0dfd057 |     False |            Have lunch tomorrow
```

#### ✅ Step 6c. Run unit test `Ex6c_Get_All_Todos.java`

Let's get all the todos associated with an user.

Take a moment to review the code with the following command

```bash
gp open src/test/java/com/datastax/workshop/Ex6c_Get_All_Todos.java
```
After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
mvn test -Dtest=com.datastax.workshop.Ex6c_Get_All_Todos
```

**6c. Test output**

**👁️ Expected output**

```bash
[should_get_todos_1] ========================================
[should_get_todos_1] Looking for John's Tasks
[should_get_todos_1] 11111111-5cff-11ec-be16-1fedb0dfd057: Walk the dog
[should_get_todos_1] 22222222-5cff-11ec-be16-1fedb0dfd057: Have lunch tomorrow
[should_get_todos_1] [OK]
[should_get_todos_1] ========================================

[should_get_todos_2] ========================================
[should_get_todos_2] Looking for 'createTask' Tasks
[should_get_todos_2] 9b0f7960-7842-11ec-ac4e-0fa3c250f26e: Task CQL
[should_get_todos_2] 9d8b00b0-7842-11ec-ac4e-0fa3c250f26e: Task Simple Statement position
[should_get_todos_2] a11e1280-7842-11ec-ac4e-0fa3c250f26e: Task Simple Statement name
[should_get_todos_2] a3a9c670-7842-11ec-ac4e-0fa3c250f26e: Prepared Task
[should_get_todos_2] [OK]
[should_get_todos_2] ========================================

[should_get_todos_3 with simple object mapper]=================
[should_get_todos_3] Looking for 'createTask' Tasks
Todoitem title = Task CQL
Todoitem title = Task Simple Statement position
Todoitem title = Task Simple Statement name
Todoitem title = Prepared Task
[should_get_todos_3 with simple object mapper] [OK]
[should_get_todos_3] ========================================
```

You can verify via the CQL shell or via the console as well as you did in previous step(s).

#### ✅ Step 6d. Run unit test `Ex6d_Update_A_Todo.java`

Let's update an item.

Take a moment to review the code with the following command

```bash
gp open src/test/java/com/datastax/workshop/Ex6d_Update_A_Todo.java
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
mvn test -Dtest=com.datastax.workshop.Ex6d_Update_A_Todo
```

**6d. Test output**

**👁️ Expected output**

```bash
[should_update_todos] ========================================
[should_update_todos] Start Exercise
[should_update_todos] Before:
+11111111-5cff-11ec-be16-1fedb0dfd057: true:Walk the dog
+22222222-5cff-11ec-be16-1fedb0dfd057: false:Have lunch tomorrow
[should_update_todos] After:
+11111111-5cff-11ec-be16-1fedb0dfd057: true:Walk the dog
+22222222-5cff-11ec-be16-1fedb0dfd057: true:Have lunch tomorrow
[should_update_todos] [OK]
[should_update_todos] ========================================

[should_update_todos_2] ========================================
[should_update_todos_2] Start Exercise
+11111111-5cff-11ec-be16-1fedb0dfd057: false:Walk the dog
+22222222-5cff-11ec-be16-1fedb0dfd057: true:Have lunch tomorrow
[should_update_todos_2] [OK]
[should_update_todos_2] ========================================
```

**6d. CQL Console**

From either the CQL shell window or the CQL console in the Astra console issue the following command to notice the items that have been created.

```cql
select * from todoitems;
```

**6d. CQL Output**

**👁️ Expected output**

```cql
 user_id    | item_id                              | completed | title
------------+--------------------------------------+-----------+--------------------------------
 createTask | 8c6b8e80-783d-11ec-bf8f-0d82d3accec9 |     False |                       Task CQL
 createTask | 8ef56db0-783d-11ec-bf8f-0d82d3accec9 |     False | Task Simple Statement position
 createTask | 91740240-783d-11ec-bf8f-0d82d3accec9 |     False |     Task Simple Statement name
 createTask | 9400eeb0-783d-11ec-bf8f-0d82d3accec9 |     False |                  Prepared Task
       mary | 33333333-5cff-11ec-be16-1fedb0dfd057 |      True |            Attend the workshop
       john | 11111111-5cff-11ec-be16-1fedb0dfd057 |      True |                   Walk the dog
       john | 22222222-5cff-11ec-be16-1fedb0dfd057 |     False |            Have lunch tomorrow

(7 rows)
```

#### ✅ Step 6e. Run unit test `Ex6e_Remove_A_Todo.java`

Let's remove an item.

Take a moment to review the code with the following command

```bash
gp open src/test/java/com/datastax/workshop/Ex6e_Remove_A_Todo.java
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
mvn test -Dtest=com.datastax.workshop.Ex6e_Remove_A_Todo
```

**6e. Test output**

```bash
[should_remove_todos] ========================================
[should_remove_todos] Start Exercise
+22222222-5cff-11ec-be16-1fedb0dfd057: true:Have lunch tomorrow
[should_remove_todos] [OK]
[should_remove_todos] ========================================
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

#### ✅ Step 6f. Run unit test `Ex6f_Remove_All_Todos.java`

Finally, let's remove all todos associated with an user.

Take a moment to review the code with the following command

```bash
gp open src/test/java/com/datastax/workshop/Ex6f_Remove_All_Todos.java
```

After reviewing the code, run the following command in the GitPod terminal window which runs the code after making the connection.

```bash
mvn test -Dtest=com.datastax.workshop.Ex6f_Remove_All_Todos
```

**6f. Test output**

**👁️ Expected output**

```bash
[should_remove_all_todos] ========================================
[should_remove_all_todos] Start Exercise
[should_remove_all_todos] [OK]
[should_remove_all_todos] ========================================
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
```


**Cleanup**

**Caution: You will lose all the data** if you run the following command in the CQL Console

```cql
DROP TABLE IF EXISTS todos.todoitems;
```

[🏠 Go back](../README.MD)
