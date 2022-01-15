# 🎓🔥 NodeJS - Back End App Dev with Cassandra 🔥🎓

![datamodel](../images/AppDevSplash.png?raw=true)

## NodeJS - Back End App Dev with Cassandra

![JAVA](https://raw.githubusercontent.com/DataStax-Academy/cassandra-workshop-series/master/materials/images/logo-javascript.png)

[🏠 Go back](../README.MD)

| Exercises |
|---|
| [Connectivity to Cassandra](#6-connectivity-to-cassandra) |

## 6. Connectivity to Cassandra 

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

**✅ Step 6a. Run unit test `Ex06_Connect_to_Astra.js`**

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

You can optionally issue the following command or notice the changes in the explorer window. 

```bash
git diff $_
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

## CRUD tests - TBD

You may run other Create, Read, Update, Delete (CRUD) tests in the same sub-directory in a similar manner.

