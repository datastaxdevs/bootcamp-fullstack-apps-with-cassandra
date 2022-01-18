# 🎓🔥 Java - Back End App Dev with Cassandra 🔥🎓

![datamodel](../images/AppDevSplash.png?raw=true)

## Java - Back End App Dev with Cassandra

![JAVA](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-java.png)

[🏠 Go back](../README.MD)

| Exercises |
|---|
| [Connectivity to Cassandra](#6-connectivity-to-cassandra) |

## 6. Connectivity to Cassandra 

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

**✅ Step 6. Run unit test `Ex06_Connect_to_Astra.java`**

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

You can optionally issue the following command or notice the changes in the explorer window. 

```bash
git diff $_
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
[INFO] 
[INFO] ---------------------< com.datastax.workshop:crud >---------------------
[INFO] Building crud 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ crud ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ crud ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ crud ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ crud ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ crud ---
[INFO] Surefire report directory: /workspace/bootcamp-fullstack-apps-with-cassandra/week3-app-development/java/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Picked up JAVA_TOOL_OPTIONS:  -Xmx2576m
Running com.datastax.workshop.Ex06_Connect_to_Astra
03:13:12.445 INFO  Exercise 6 : ========================================
03:13:12.448 INFO  Exercise 6 : Start exercise
03:13:12.451 INFO  Exercise 6 : File /workspace/bootcamp-fullstack-apps-with-cassandra/secure-connect-workshops.zip located
03:13:16.500 INFO  Exercise 6 : Connected with Keyspace todos
03:13:18.522 INFO  Exercise 6 : SUCCESS
03:13:18.522 INFO  Exercise 6 : ========================================
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.643 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.580 s
[INFO] Finished at: 2022-01-13T03:13:18Z
[INFO] ------------------------------------------------------------------------
```

## Create Read Update Delete

**✅ Step 6a. Run unit test `Ex6a_Create_Table.java`**

```bash
mvn test -Dtest=com.datastax.workshop.Ex6a_Create_Table
```

**6a. Test output**

```bash
09:17:50.336 [35mINFO [0;39m [36mExercise 6a[0;39m : ========================================
09:17:50.338 [35mINFO [0;39m [36mExercise 6a[0;39m : should_create_a_table
09:17:53.960 [35mINFO [0;39m [36mExercise 6a[0;39m : Connected with Keyspace todos
09:17:54.029 [35mINFO [0;39m [36mExercise 6a[0;39m : Table todoitems created if needed
09:17:56.092 [35mINFO [0;39m [36mExercise 6a[0;39m : [OK]
```

**6a. Cql Console**

```sql
use todos;
DECBRIBE TABLE todoitems;
```

**6a. Cql output**

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

**✅ Step 6b. Run unit test `Ex6b_Insert_Todos.java`**

```bash
mvn test -Dtest=com.datastax.workshop.Ex6b_Insert_Todos
```

**6b. Test output**

```bash
09:31:04.006 [35mINFO [0;39m [36mExercise 6b[0;39m : ========================================
09:31:04.007 [35mINFO [0;39m [36mExercise 6b[0;39m : Insert CQL 1
09:31:07.605 [35mINFO [0;39m [36mExercise 6b[0;39m : Connected with Keyspace todos
09:31:07.679 [35mINFO [0;39m [36mExercise 6b[0;39m : Task '11111111-5cff-11ec-be16-1fedb0dfd057' has been inserted
09:31:07.735 [35mINFO [0;39m [36mExercise 6b[0;39m : Task '22222222-5cff-11ec-be16-1fedb0dfd057' has been inserted
09:31:07.793 [35mINFO [0;39m [36mExercise 6b[0;39m : Task '33333333-5cff-11ec-be16-1fedb0dfd057' has been inserted
09:31:09.866 [35mINFO [0;39m [36mExercise 6b[0;39m : [OK]
09:31:09.873 [35mINFO [0;39m [36mExercise 6b[0;39m : ========================================
09:31:09.873 [35mINFO [0;39m [36mExercise 6b[0;39m : Insert CQL 2
09:31:14.082 [35mINFO [0;39m [36mExercise 6b[0;39m : [OK]
09:31:14.084 [35mINFO [0;39m [36mExercise 6b[0;39m : ========================================
09:31:14.084 [35mINFO [0;39m [36mExercise 6b[0;39m : Insert Simple Statement (position)
09:31:16.475 [35mINFO [0;39m [36mExercise 6b[0;39m : Task '00d76d70-7839-11ec-8113-894c6aa07015' has been inserted
09:31:18.531 [35mINFO [0;39m [36mExercise 6b[0;39m : [OK]
09:31:18.532 [35mINFO [0;39m [36mExercise 6b[0;39m : ========================================
09:31:18.532 [35mINFO [0;39m [36mExercise 6b[0;39m : Insert Simple Statement (named)
09:31:20.634 [35mINFO [0;39m [36mExercise 6b[0;39m : Task '03519530-7839-11ec-8113-894c6aa07015' has been inserted
09:31:22.706 [35mINFO [0;39m [36mExercise 6b[0;39m : [OK]
09:31:22.708 [35mINFO [0;39m [36mExercise 6b[0;39m : ========================================
09:31:22.708 [35mINFO [0;39m [36mExercise 6b[0;39m : Insert Prepared Statement
09:31:24.959 [35mINFO [0;39m [36mExercise 6b[0;39m : Task '05e5ad90-7839-11ec-8113-894c6aa07015' has been inserted
09:31:27.012 [35mINFO [0;39m [36mExercise 6b[0;39m : [OK]
```

**6b. Cql Console**

```sql
select * from todoitems;
```

**6b. Cql Output**

```bash

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

**✅ Step 6c. Run unit test `Ex6c_Get_All_Todos.java`**

```bash
mvn test -Dtest=com.datastax.workshop.Ex6c_Get_All_Todos
```

**6c. Test output**

```bash
09:42:12.702 [35mINFO [0;39m [36mExercise 6c[0;39m : ========================================
09:42:12.703 [35mINFO [0;39m [36mExercise 6c[0;39m : should_get_todos (john)
09:42:16.341 [35mINFO [0;39m [36mExercise 6c[0;39m : 11111111-5cff-11ec-be16-1fedb0dfd057: Walk the dog
09:42:16.341 [35mINFO [0;39m [36mExercise 6c[0;39m : 22222222-5cff-11ec-be16-1fedb0dfd057: Have lunch tomorrow
09:42:18.406 [35mINFO [0;39m [36mExercise 6c[0;39m : [OK]
09:42:18.413 [35mINFO [0;39m [36mExercise 6c[0;39m : ========================================
09:42:18.413 [35mINFO [0;39m [36mExercise 6c[0;39m : should_get_todos_2 (createTask)
09:42:20.509 [35mINFO [0;39m [36mExercise 6c[0;39m : fe2f09c0-7838-11ec-8113-894c6aa07015: Task CQL
09:42:20.509 [35mINFO [0;39m [36mExercise 6c[0;39m : 00d76d70-7839-11ec-8113-894c6aa07015: Task Simple Statement position
09:42:20.509 [35mINFO [0;39m [36mExercise 6c[0;39m : 03519530-7839-11ec-8113-894c6aa07015: Task Simple Statement name
09:42:20.509 [35mINFO [0;39m [36mExercise 6c[0;39m : 05e5ad90-7839-11ec-8113-894c6aa07015: Prepared Task
09:42:22.578 [35mINFO [0;39m [36mExercise 6c[0;39m : [OK]
09:42:22.578 [35mINFO [0;39m [36mExercise 6c[0;39m : ========================================
```

**✅ Step 6d. Run unit test `Ex6d_Update_A_Todo.java`**

```bash
mvn test -Dtest=com.datastax.workshop.Ex6d_Update_A_Todo
```

**6d. Test output**

```bash
10:04:09.399 [35mINFO [0;39m [36mExercise 6d[0;39m : ========================================
10:04:09.400 [35mINFO [0;39m [36mExercise 6d[0;39m : should_update_todos 1
10:04:12.973 [35mINFO [0;39m [36mExercise 6d[0;39m : Before
10:04:13.046 [35mINFO [0;39m [36mExercise 6d[0;39m : +11111111-5cff-11ec-be16-1fedb0dfd057: true:Walk the dog
10:04:13.046 [35mINFO [0;39m [36mExercise 6d[0;39m : +22222222-5cff-11ec-be16-1fedb0dfd057: false:Have lunch tomorrow
10:04:13.096 [35mINFO [0;39m [36mExercise 6d[0;39m : After:
10:04:13.151 [35mINFO [0;39m [36mExercise 6d[0;39m : +11111111-5cff-11ec-be16-1fedb0dfd057: true:Walk the dog
10:04:13.151 [35mINFO [0;39m [36mExercise 6d[0;39m : +22222222-5cff-11ec-be16-1fedb0dfd057: true:Have lunch tomorrow
10:04:15.236 [35mINFO [0;39m [36mExercise 6d[0;39m : [OK]
10:04:15.244 [35mINFO [0;39m [36mExercise 6d[0;39m : ========================================
10:04:15.244 [35mINFO [0;39m [36mExercise 6d[0;39m : should_update_todos 2
10:04:17.393 [35mINFO [0;39m [36mExercise 6d[0;39m : +11111111-5cff-11ec-be16-1fedb0dfd057: false:Walk the dog
10:04:17.393 [35mINFO [0;39m [36mExercise 6d[0;39m : +22222222-5cff-11ec-be16-1fedb0dfd057: true:Have lunch tomorrow
10:04:19.471 [35mINFO [0;39m [36mExercise 6d[0;39m : [OK]
10:04:19.471 [35mINFO [0;39m [36mExercise 6d[0;39m : ========================================
```

**6d. Cql Console**

```sql
select * from todoitems;
```

**6d. Cql Output**

```bash
 user_id    | item_id                              | completed | title
------------+--------------------------------------+-----------+--------------------------------
 createTask | 8c6b8e80-783d-11ec-bf8f-0d82d3accec9 |     False |                       Task CQL
 createTask | 8ef56db0-783d-11ec-bf8f-0d82d3accec9 |     False | Task Simple Statement position
 createTask | 91740240-783d-11ec-bf8f-0d82d3accec9 |     False |     Task Simple Statement name
 createTask | 9400eeb0-783d-11ec-bf8f-0d82d3accec9 |     False |                  Prepared Task
       mary | 33333333-5cff-11ec-be16-1fedb0dfd057 |      True |            Attend the workshop
       john | 11111111-5cff-11ec-be16-1fedb0dfd057 |      True |                   Walk the dog
       john | 22222222-5cff-11ec-be16-1fedb0dfd057 |      True |            Have lunch tomorrow

(7 rows)
```

**✅ Step 6e. Run unit test `Ex6e_Remove_A_Todo.java`**

```bash
mvn test -Dtest=com.datastax.workshop.Ex6e_Remove_A_Todo
```

**6e. Test output**

```bash
xxx
```

**6e. Cql Console**

```sql
create
```

**6e. Cql Output**

```bash
xx
```

**✅ Step 6f. Run unit test `Ex6f_Remove_All_Todos.java`**

```bash
mvn test -Dtest=com.datastax.workshop.Ex6f_Remove_All_Todos
```

**6f. Test output**

```bash
xxx
```

**6f. Cql Console**

```sql
create
```

**6f. Cql Output**

```bash
xx
```
