#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise')
try:
    session.execute("""CREATE TABLE IF NOT EXISTS todoitems (
                        user_id         TEXT,
                        item_id         TIMEUUID,
                        title           TEXT,
                        completed       BOOLEAN,
                        PRIMARY KEY ((user_id), item_id)
                    ) WITH CLUSTERING ORDER BY (item_id ASC);
                    """)
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
