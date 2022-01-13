#!/usr/bin/env python3
from db_connection import Connection

print('========================================')
print('Start exercise')
try:
	connection = Connection()
	output = connection.session.execute("SELECT * FROM system.local")
	for row in output:
	    print ('You are now connected to cluster %s at %s' %(row.cluster_name, row.data_center))
except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
finally:
	print('Closing connection (up to 10s)')
	connection.close()
print('========================================')
