#!/usr/bin/env python3
from connection import session

print('========================================')
print('Start exercise')
try:
    output = session.execute("SELECT * FROM todoitems WHERE user_id = 'john';")

    lslist = [str(row).split(' , ') for row in output if 'False' in str(row)]
    #print(lslist)
    for item in lslist[0]:
        print(item)
    """ for row in output:
        #print(str(row).split(' , '))
        if 'False' in str(row):
            print(str(row).split(' , ')) """                 

except Exception as e:
    print(e)
    print('Failure')
else:
    print('Success')
print('========================================')
