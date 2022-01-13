#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise')
try:
    session.execute("TRUNCATE TABLE todoitems;")
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
