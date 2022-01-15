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

**✅ Step 6a. Run unit test `Ex06_Connect_to_Cassandra.java`**

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
mvn test -Dtest=com.datastax.workshop.Ex06_Connect_to_Cassandra
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
Running com.datastax.workshop.Ex06_Connect_to_Cassandra
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

## CRUD tests - TBD

You may run other Create, Read, Update, Delete (CRUD) tests in the same sub-directory in a similar manner. Periodically, verify from the CQL Shell in Gitpod that the programs/tests are working as expected.
