# Python code to find non-overlapping maximal cliques = minimum acceptable number of containers
# Explanation:
# 1. Graph Representation: The adjacency list represents the connections as described.
# 2. Bron-Kerbosch Algorithm: This algorithm is used to find all maximal cliques in an undirected graph.
# 3. Finding Non-overlapping Cliques:
#   	The find_cliques function uses the Bron-Kerbosch algorithm to find all cliques.
#       The find_non_overlapping_cliques function selects the largest cliques ensuring no node overlap by sorting all cliques by size and iterating through them, ensuring nodes are used only once.

import json

json_file = 'Project Dataset/OneSport/DataMetrics.json'

def bron_kerbosch(R, P, X, graph, cliques):
    if not P and not X:
        cliques.append(R)
        return
    for v in list(P):
        bron_kerbosch(R | {v}, P & set(graph[v]), X & set(graph[v]), graph, cliques)
        P.remove(v)
        X.add(v)

def find_cliques(graph):
    cliques = []
    nodes = set(graph.keys())
    bron_kerbosch(set(), nodes, set(), graph, cliques)
    return cliques

def find_non_overlapping_cliques(graph):
    all_cliques = find_cliques(graph)
    all_cliques.sort(key=len, reverse=True)  # Sort cliques by size in descending order
    
    for node in set(graph.keys()):
        node = {node}
        all_cliques.append(node)
    
    selected_cliques = []
    used_nodes = set()
    print("all cliques:", all_cliques)
    for clique in all_cliques:
        if not any(node in used_nodes for node in clique):
            selected_cliques.append(clique)
            used_nodes.update(clique)
    
    return selected_cliques

def fromSetsToGraph(sets):
    graph = {}
    for elem in sets:
        graph[elem['set_id']] = elem['links']
    return graph


f = open(json_file)
sets = json.load(f)
graph = fromSetsToGraph(sets)
non_overlapping_cliques = find_non_overlapping_cliques(graph)
print("Non-overlapping maximal cliques:", non_overlapping_cliques)
print("Number of non-overlapping cliques:", len(non_overlapping_cliques))
