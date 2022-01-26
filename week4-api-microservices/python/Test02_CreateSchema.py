#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise by dropping and creating table')
try:
    session.execute("""DROP TABLE IF EXISTS todoitems;""")
    session.execute("""CREATE TABLE IF NOT EXISTS todoitems (
                        user_id         TEXT,
                        item_id         TIMEUUID,
                        title           TEXT,
                        url             TEXT,
                        completed       BOOLEAN,
                        offset          INT,
                        PRIMARY KEY ((user_id), item_id)
                    ) WITH CLUSTERING ORDER BY (item_id ASC);
                    """)
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
