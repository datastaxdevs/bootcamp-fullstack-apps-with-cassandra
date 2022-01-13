#!/usr/bin/env python3
import uuid
from connection import session

print('========================================')
print('Start exercise')
try:
    session.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) VALUES ( 'john', 11111111-5cff-11ec-be16-1fedb0dfd057, true, 'Walk the dog');")
    session.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) VALUES ( 'john', 22222222-5cff-11ec-be16-1fedb0dfd057, false, 'Have lunch tomorrow');")
    session.execute(
        "INSERT INTO todoitems (user_id, item_id, completed, title) VALUES ( 'mary', 33333333-5cff-11ec-be16-1fedb0dfd057, true, 'Attend the workshop');")
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
