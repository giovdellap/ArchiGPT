def fromSetsToGraph(sets):
    obj = {}
    for elem in sets:
        obj[elem['set_id']] = elem['links']
    return obj