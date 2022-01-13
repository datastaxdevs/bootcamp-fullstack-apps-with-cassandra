#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise')
try:
    output = session.execute("SELECT * FROM todoitems WHERE user_id = 'john';")
    for row in output:
        print(str(row))
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
