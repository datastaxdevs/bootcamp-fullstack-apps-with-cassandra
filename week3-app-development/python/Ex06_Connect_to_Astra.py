#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise')
try:
    output = session.execute("SELECT * FROM system.local")
    for row in output:
        print('You are now connected to cluster %s at %s' %
              (row.cluster_name, row.data_center))
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
