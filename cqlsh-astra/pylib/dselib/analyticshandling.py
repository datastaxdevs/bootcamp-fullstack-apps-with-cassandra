# Copyright DataStax, Inc.
#
# Please see the included license file for details.
#

from cqlshlib.cql3handling import CqlRuleSet

explain_completion = CqlRuleSet.explain_completion

analytics_syntax_rules = r'''

<analyticsStatement> ::= <listNodesStatement>
                       | <listNodeStatement>
                       | <planApplicationsStatement>
                       | <createApplicationStatement>
                       | <listApplicationStatement>
                       | <listApplicationsStatement>
                       | <dropApplicationStatement>
                       | <alterApplicationStatement>
                       | <listExecutorStatement>
                       | <listExecutorsOfApplicationStatement>
                       | <listExecutorsOfNodeStatement>
                       | <dropExecutorStatement>
                       | <listExecutorLogStatement>
                       | <listApplicationConfigStatement>
                       | <listClusterStatement>
                       ;
                            
<listNodesStatement> ::= "LIST" "NODES" ("OF" <dcName>)?
                       ;

<listNodeStatement> ::= "LIST" "NODE" <nodeId>
                      ;

<planApplicationsStatement> ::= "PLAN" "APPLICATIONS" ("OF" <dcName>)?
                              ;
           
<createApplicationStatement> ::= "CREATE" "JAVA" "APPLICATION" (<dcName> ".")?<appName> "WITH" <property> ("AND" <property>)*
                               ;

<listApplicationStatement> ::= "LIST" "APPLICATION" (<dcName> ".")? <appId>
                             ;

<listApplicationsStatement> ::= "LIST" "ACTIVE"? "APPLICATIONS" ("OF" <dcName>)?
                              ;

<dropApplicationStatement> ::= "DROP" "APPLICATION" (<dcName> ".")? <appId>
                             ;

<alterApplicationStatement> ::= "ALTER" "APPLICATION" (<dcName> ".")?<appId> "WITH" <property> ("AND" <property>)*
                              ;

<listExecutorStatement> ::= "LIST" "EXECUTOR" <executorId> "OF" "APPLICATION" (<dcName> ".")? <appId>
                          ;
   
<listExecutorsOfApplicationStatement> ::= "LIST" "EXECUTORS" "OF" "APPLICATION" (<dcName> ".")? <appId>
                                        ;

<listExecutorsOfNodeStatement> ::= "LIST" "EXECUTORS" "ON" "NODE" <nodeId>
                                 ;

<dropExecutorStatement> ::= "DROP" "EXECUTOR" <executorId> "OF" "APPLICATION" (<dcName> ".")? <appId> ("WITH" <property> ( "AND" <property> )*)?
                          ;

<listExecutorLogStatement> ::= "LIST" ("STD_OUT"|"STD_ERR") "FROM" <logOffset> "TO" <logOffset> "ON" "EXECUTOR" <executorId> "OF" "APPLICATION" (<dcName> ".")? <appId>
                             ;
                          
<listApplicationConfigStatement> ::= "LIST" "APPLICATION" "CONFIG" (<dcName> ".")? <appId> ("NO" "REDACT")?
                             ;
                             
<listClusterStatement> ::= "LIST" "CLUSTER"
                             ;

<appId> ::= appId=<uuid>
          ;

<appName> ::= appName=(<stringLiteral>|<identifier>)
            ;       

<executorId> ::= executorId=<uuid>
               ;

<nodeId> ::= nodeId=<uuid>
           ;

<dcName> ::= dcName=(<stringLiteral>|<identifier>)
           ;

<logOffset> ::= logOffset=<integer>
              ;
'''

explain_completion('appId', 'appId', '<app_uuid>')
explain_completion('appName', 'appName', '<app_name>')
explain_completion('executorId', 'executorId', '<executor_uuid>')
explain_completion('nodeId', 'nodeId', '<node_uuid>')
explain_completion('dcName', 'dcName', '<dc_name>')
explain_completion('logOffset', 'logOffset', '<byte_offset>')
