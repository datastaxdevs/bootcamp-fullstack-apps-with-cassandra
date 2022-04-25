#!/usr/bin/env python3
import uuid
from connection import session

print('========================================')
print('Start exercise')
try:
    session.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) VALUES ( 'kelvin', 22222222-5cff-11ec-be16-1fedb0dfd056, false, 'Have lunch tomorrow');"
        )
    session.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) VALUES ( 'grace', 33333333-5cff-11ec-be16-1fedb0dfd056, false, 'One week workshop assignment left');"
    ) 

    output = session.execute(
        "SELECT * FROM todoitems;"
    )   
    print([row for row in output])
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')