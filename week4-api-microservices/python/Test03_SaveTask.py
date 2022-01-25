#!/usr/bin/env python3
import uuid
from connection import session

print('========================================')
print('Start exercise')
try:
    session.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title, offset) VALUES ( 'john', 11111111-5cff-11ec-be16-1fedb0dfd057, true, 'Walk the dog', 0);")
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
