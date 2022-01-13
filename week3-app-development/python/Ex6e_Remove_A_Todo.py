#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise')
try:
    session.execute(
        "DELETE FROM todoitems WHERE user_id='john' AND item_id=11111111-5cff-11ec-be16-1fedb0dfd057;")
    output = session.execute(
        "SELECT toTimestamp(item_id), completed, title FROM todoitems WHERE user_id = 'john';")
    for row in output:
        print(row)
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
