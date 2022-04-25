# Copyright DataStax, Inc.
#
# Please see the included license file for details.
#
from cqlshlib.cql3handling import CqlRuleSet, cf_prop_val_mapkey_completer as original_cf_prop_val_mapkey_completer, \
    cf_prop_val_mapval_completer as original_cf_prop_val_mapval_completer

MemoryOnlyStrategy = 'MemoryOnlyStrategy'
TieredCompactionStrategy = 'TieredCompactionStrategy'

CqlRuleSet.available_compaction_classes = CqlRuleSet.available_compaction_classes + (
    MemoryOnlyStrategy, TieredCompactionStrategy)

# convenience for remainder of module
dequote_value = CqlRuleSet.dequote_value
dequote_name = CqlRuleSet.dequote_name
escape_value = CqlRuleSet.escape_value

memory_only_strategy_options = (
    'mos_min_threshold',
    'mos_max_threshold',
    'mos_max_active_compactions'
)

tiered_compaction_strategy_options = (
    'max_tier_ages',
    'tiering_strategy',
    'config'
)

tiered_compaction_available_strategies = (
    'TimeWindowStorageStrategy',
    'DateTieredStorageStrategy'
)


def dse_cf_prop_val_mapkey_completer(ctxt, cass):
    # OSS code start
    optname = ctxt.get_binding('propname')[-1]
    for cql3option, _, subopts in CqlRuleSet.columnfamily_layout_map_options:
        if optname == cql3option:
            break
    else:
        return ()
    keysseen = map(dequote_value, ctxt.get_binding('propmapkey', ()))
    valsseen = map(dequote_value, ctxt.get_binding('propmapval', ()))
    pairsseen = dict(zip(keysseen, valsseen))

    if optname == 'compaction':
        opts = set(subopts)
        try:
            csc = pairsseen['class']
        except KeyError:
            return ["'class'"]
        csc = csc.split('.')[-1]
        # OSS code end

        if csc == MemoryOnlyStrategy:
            opts = opts.union(set(memory_only_strategy_options))
        elif csc == TieredCompactionStrategy:
            opts = opts.union(set(tiered_compaction_strategy_options))

            storage_strategy = pairsseen.get('tiering_strategy', None)
            # show suboptions of underlying compaction strategy
            if storage_strategy:
                storage_strategy = storage_strategy.split('.')[-1]
                if storage_strategy == 'DateTieredStorageStrategy':
                    opts = opts.union(CqlRuleSet.date_tiered_compaction_strategy_options)
                if storage_strategy == 'TimeWindowStorageStrategy':
                    opts = opts.union(CqlRuleSet.time_window_compaction_strategy_options)
        else:
            # use the original completer for all remaining compaction strategies
            return original_cf_prop_val_mapkey_completer(ctxt, cass)
        return map(escape_value, opts)

    # use the original completer for caching & compression
    return original_cf_prop_val_mapkey_completer(ctxt, cass)


def dse_cf_prop_val_mapval_completer(ctxt, cass):
    """
    This is for properly showing the available tiering strategies when TieredCompactionStrategy
    was selected in the option 'tiering_strategy'
    """
    opt = ctxt.get_binding('propname')[-1]
    key = dequote_value(ctxt.get_binding('propmapkey')[-1])
    if opt == 'compaction' and key == 'tiering_strategy':
        return map(escape_value, tiered_compaction_available_strategies)

    # use the original completer for everything else
    return original_cf_prop_val_mapval_completer(ctxt, cass)
