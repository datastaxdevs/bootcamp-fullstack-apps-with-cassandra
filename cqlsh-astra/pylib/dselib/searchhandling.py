# Copyright DataStax, Inc.
#
# Please see the included license file for details.
#
from cqlshlib.cqlhandling import Hint
from cqlshlib.cql3handling import CqlRuleSet, get_table_meta, regular_column_names, dequote_name
from cassandra.metadata import maybe_escape_name


completer_for = CqlRuleSet.completer_for
explain_completion = CqlRuleSet.explain_completion

search_syntax_rules = r'''
<atsymbol> ::= "@";

<searchStatement> ::= <createSearchIndexStatement>
                    | <alterSearchIndexStatement>
                    | <reloadSearchIndexStatement>
                    | <dropSearchIndexStatement>
                    | <rebuildSearchIndexStatement>
                    | <commitSearchIndexStatement>
                    ;

<createSearchIndexStatement> ::= "CREATE" "SEARCH" "INDEX" ("IF" "NOT" "EXISTS")? "ON" cf=<columnFamilyName>
                                 ( "WITH" <createSearchIndexOption> ("AND" <createSearchIndexOption>)* )?
                               ;

<createSearchIndexOption> ::= "COLUMNS" ( "*" <createSearchIndexColumnOptionMap> | <createSearchIndexColumnList>)
                            | "PROFILES" <createSearchIndexProfile> ("," <createSearchIndexProfile>)*
                            | "CONFIG" <createSearchIndexConfigOptionMap>
                            | "OPTIONS" <createSearchIndexRequestOptionMap>
                            ;

<createSearchIndexColumnList> ::= <createSearchIndexColumn> ("," <createSearchIndexColumn>)*
                                ;

<createSearchIndexColumn> ::= [colname]=<cident> ( <createSearchIndexColumnOptionMap> )?
                            ;

<createSearchIndexColumnOptionMap> ::= "{" (<createSearchIndexColumnOption> ":" <boolean>
                                            ( "," <createSearchIndexColumnOption> ":" <boolean>)*)?
                                       "}"
                                      ;

<createSearchIndexColumnOption> ::= "indexed"
                                  | "docValues"
                                  | "copyField"
                                  | "excluded"
                                  ;

<createSearchIndexProfile> ::= "spaceSavingAll"
                             | "spaceSavingNoJoin"
                             | "spaceSavingSlowTriePrecision"
                             ;

<createSearchIndexConfigOptionMap> ::= "{" (<createSearchIndexConfigOption> ":" <createSearchIndexConfigOptionValue>
                                            ( "," <createSearchIndexConfigOption> ":" <createSearchIndexConfigOptionValue>)*)?
                                       "}"
                                     ;

<createSearchIndexConfigOption> ::= configoption=("defaultQueryField"
                                                  | "autoCommitTime"
                                                  | "directoryFactory"
                                                  | "directoryFactoryClass"
                                                  | "mergeMaxThreadCount"
                                                  | "mergeMaxMergeCount"
                                                  | "filterCacheLowWaterMark"
                                                  | "filterCacheHighWaterMark"
                                                  | "typeMappingVersion"
                                                  | "realtime")
                                  ;

<createSearchIndexRequestOptionMap> ::= "{" (<createSearchIndexRequestOption> ":" <boolean>
                                             ( "," <createSearchIndexRequestOption> ":" <boolean>)*)?
                                        "}"
                                      ;

<createSearchIndexRequestOption> ::= "recovery"
                                   | "reindex"
                                   | "lenient"
                                   ;

<createSearchIndexConfigOptionValue> ::= configoptionvalue=(<stringLiteral>
                                                            | <float>
                                                            | <wholenumber>
                                                            | <boolean> )
                                       ;

<alterSearchIndexStatement> ::= "ALTER" "SEARCH" "INDEX" resourcetype=("CONFIG" | "SCHEMA") "ON" cf=<columnFamilyName>
                                (
                                    "ADD" element=<elementPath> (("WITH" <jsonLiteral>) | ([colname]=<cident>))?
                                  | "SET" <elementPath> ( "@" setOrDropAttribute=<identifier> )? setequals="=" <attributeValue>
                                  | "DROP" element=<elementPath> ( ("@" setOrDropAttribute=<identifier>) | ([colname]=<cident>))?
                                )
                              ;

<elementPath> ::= [elements]=(<identifier>) ("[" <searchResourceAttribute> ("," <searchResourceAttribute>)*"]")?
                  ("." [elements]=(<identifier>) ("[" <searchResourceAttribute> ("," <searchResourceAttribute>)*"]")?)*
                ;

<searchResourceAttribute> ::= "@" [attributes]=<identifier> "=" attributevalue=<attributeValue>
                            ;

<attributeValue> ::= attributevalue=<stringLiteral>
                   ;

<reloadSearchIndexStatement> ::= "RELOAD" "SEARCH" "INDEX" "ON" cf=<columnFamilyName>
                               ;

<dropSearchIndexStatement> ::= "DROP" "SEARCH" "INDEX" "ON" cf=<columnFamilyName>
                               ("WITH" "OPTIONS" <dropSearchIndexRequestOptionMap>)?
                             ;

<dropSearchIndexRequestOptionMap> ::= "{" (<dropSearchIndexRequestOption> ":" <boolean>
                                           ( "," <dropSearchIndexRequestOption> ":" <boolean>)*)?
                                      "}"
                                      ;

<dropSearchIndexRequestOption> ::= "deleteResources"
                                 | "deleteDataDir"
                                 ;

<rebuildSearchIndexStatement> ::= "REBUILD" "SEARCH" "INDEX" "ON" cf=<columnFamilyName>
                                  ("WITH" "OPTIONS" <rebuildSearchIndexRequestOptionMap>)?
                                ;

<rebuildSearchIndexRequestOptionMap> ::= "{" "deleteAll" ":" <boolean> "}"
                                       ;

<commitSearchIndexStatement> ::= "COMMIT" "SEARCH" "INDEX" "ON" cf=<columnFamilyName>
                               ;
'''

@completer_for('createSearchIndexColumn', 'colname')
def create_search_index_colname_completer(ctxt, cass):
    layout = get_table_meta(ctxt, cass)
    colnames = set(map(dequote_name, ctxt.get_binding('colname', ())))
    newcols = set(layout.columns.keys()) - colnames
    return map(maybe_escape_name, newcols)

@completer_for('createSearchIndexConfigOptionValue', 'configoptionvalue')
def config_value_completer(ctxt, cass):
    option = ctxt.get_binding('configoption')
    if option in ['autoCommitTime', 'mergeMaxThreadCount',
                  'mergeMaxMergeCount', 'filterCacheLowWaterMark', 'filterCacheLowWaterMark']:
        return [Hint('<wholenumber>')]
    elif option == 'directoryFactory':
        return ["'encrypted'", "'standard'"]
    elif option == 'typeMappingVersion':
        return ['2']
    elif option == 'realtime':
        return ['true', 'false']
    else:
        return [Hint('<stringLiteral')]

@completer_for('alterSearchIndexStatement', 'colname')
def create_search_index_colname_completer(ctxt, cass):
    element = ctxt.get_binding('element')
    if element == 'field':
        layout = get_table_meta(ctxt, cass)
        colnames = set(layout.columns.keys())
        if 'solr_query' in colnames:
            colnames.remove('solr_query')
        return map(maybe_escape_name, colnames)
    else:
        return {}

@completer_for('elementPath', 'elements')
def element_name_completer(ctxt, cass):
    resourceType = ctxt.get_binding('resourcetype')
    if resourceType.lower() == "schema":
        elements = ctxt.get_binding('elements', ())
        if not elements:
            return ['field', 'fieldType', 'copyField', 'dynamicField', 'similarity']
        else:
            first_element = elements[0]
            last_element = elements[-1]
            if first_element == 'fieldType':
                if last_element == 'fieldType':
                    return ['analyzer']
                elif last_element == 'analyzer':
                    return ['tokenizer', 'filter']
            return []
    else:
        elements = ctxt.get_binding('elements', ())
        if not elements:
            return ['autoCommitTime', 'defaultQueryField', 'directoryFactory',
                    'mergeMaxThreadCount', 'mergeMaxMergeCount',
                    'realtime', 'filterCacheLowWaterMark', 'filterCacheHighWaterMark']
        else:
            return [Hint('<configXmlElement>')]

def get_all_attributes_for_element(elementName):
    if elementName == 'copyField':
        return ['source', 'dest', 'maxChars']
    elif elementName in ['field', 'dynamicField']:
        return ['name', 'type', 'default',
                'indexed', 'stored', 'docValues',
                'sortMissingFirst', 'sortMissingLast', 'multiValued',
                'omitNorms', 'omitTermFreqAndPositions', 'omitPositions',
                'termVectors', 'termPositions', 'termOffsets', 'termPayloads',
                'required', 'useDocValuesAsStored']
    elif elementName == 'fieldType':
        return ['name', 'class', 'positionIncrementGap', 'precisionStep', 'autoGeneratePhraseQueries',
                'docValuesFormat', 'postingsFormat',
                'indexed', 'stored', 'docValues',
                'sortMissingFirst', 'sortMissingLast', 'multiValued',
                'omitNorms', 'omitTermFreqAndPositions', 'omitPositions',
                'termVectors', 'termPositions', 'termOffsets', 'termPayloads',
                'required', 'useDocValuesAsStored']
    elif elementName == 'analyzer':
        return ['type']
    elif elementName == 'tokenizer':
        return ['class']
    elif elementName == 'filter':
        return ['class']
    else:
        return []

@completer_for('searchResourceAttribute', 'attributes')
def attributes_completer(ctxt, cass):
    elementName = ctxt.get_binding('elements')[-1]
    all_attributes = get_all_attributes_for_element(elementName)
    if not all_attributes:
        return [Hint('<attributeName>')]
    else:
        return all_attributes

boolean_attributes = [ 'indexed', 'stored', 'docValues', 'autoGeneratePhraseQueries',
                       'sortMissingFirst', 'sortMissingLast', 'multiValued',
                       'omitNorms', 'omitTermFreqAndPositions', 'omitPositions',
                       'termVectors', 'termPositions', 'termOffsets', 'termPayloads',
                       'required', 'useDocValuesAsStored']

int_attributes = [ 'precisionStep', 'positionIncrementGap' ]

@completer_for('alterSearchIndexStatement', 'setOrDropAttribute')
def set_or_drop_attribute_completer(ctxt, cass):
    elementName = ctxt.get_binding('elements')[-1]
    all_attributes = get_all_attributes_for_element(elementName)
    if not all_attributes:
        return [Hint('<attributeName>')]
    else:
        return all_attributes

@completer_for('attributeValue', 'attributevalue')
def attribute_value_completer(ctxt, cass):
    resourceType = ctxt.get_binding('resourcetype')
    if resourceType.lower() == "schema":
        set_equals = ctxt.get_binding('setequals')
        if not set_equals:
            last_attribute = ctxt.get_binding('attributes')[-1]
            if last_attribute in boolean_attributes:
                return ["'true'", "'false'"]
            elif last_attribute in int_attributes:
                return [Hint("<wholeNumber>")]
            else:
                return [Hint("<stringLiteral>")]
        else:
            set_attribute = ctxt.get_binding('setOrDropAttribute')
            if set_attribute in boolean_attributes:
                return ["true", "false"]
            elif set_attribute in int_attributes:
                return [Hint("<wholeNumber>")]
            else:
                return [Hint("<stringLiteral>")]
    else:
        set_equals = ctxt.get_binding('setequals')
        elementName = ctxt.get_binding('elements')[-1]
        if set_equals:
            if elementName == 'autoCommitTime':
                return [Hint("<wholeNumber>")]
            elif elementName == 'defaultQueryField':
                return [Hint("<stringLiteral>")]
            elif elementName == 'directoryFactory':
                return ['standard', 'encrypted']
            elif elementName == 'mergeMaxThreadCount':
                return [Hint("<wholeNumber>")]
            elif elementName == 'mergeMaxMergeCount':
                return [Hint("<wholeNumber>")]
            elif elementName == 'realtime':
                return ["'true'", "'false'"]
            elif elementName == 'filterCacheLowWaterMark':
                return [Hint("<wholeNumber>")]
            elif elementName == 'filterCacheHighWaterMark':
                return [Hint("<wholeNumber>")]
        return [Hint("<attributeValue>")]
