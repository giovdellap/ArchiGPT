// Metric Name: Granularity Evaulation
// num_clique = numero not overlapping cliques (found by maxNotOverllapingCliques.py)
//                      if num_c <= num_clique             | = 100*(num_c/num_clique)
//  metric_result =     if num_clique < num_c < num_set    | = 100
//                      if num_c >= num_set                | = 100*(2*num_set-num_c)/num_set

function geMetrics(projectData, benchmarkData) {

    const num_c = projectData.containers.length
    const num_set = benchmarkData.metrics.length
    const num_clique =  maxNotOverllapingCliques(benchmarkData.metrics)
    let result = 0

    if ( num_c <= num_clique ) result = 100 * (num_c/num_clique)
    if ( num_clique <= num_c && num_c <= num_set ) result = 100
    if ( num_c >= num_set ) result = 100 * (2*num_set-num_c)/num_set

    return result
}

module.exports = {
    geMetrics
}


// JavaScript code to find non-overlapping maximal cliques = minimum acceptable number of containers
// Explanation:
// 1. Graph Representation: The adjacency list represents the connections as described.
// 2. Bron-Kerbosch Algorithm: This algorithm is used to find all maximal cliques in an undirected graph.
// 3. Finding Non-overlapping Cliques:
//   	The find_cliques function uses the Bron-Kerbosch algorithm to find all cliques.
//      The find_non_overlapping_cliques function selects the largest cliques ensuring no node overlap by sorting all cliques by size and iterating through them, ensuring nodes are used only once.

function maxNotOverllapingCliques(setData){

    // Bron-Kerbosch Algorithm to find cliques
    function bronKerbosch(R, P, X, graph, cliques) {

        if (P.size === 0 && X.size === 0) {
            cliques.push(new Set(R));
            return;
        }
        for (let v of Array.from(P)) {
            const neighbors = new Set((graph[v] || []).map(link => String(link)));
            bronKerbosch(
                new Set([...R, v]),
                new Set([...P].filter(p => neighbors.has(p))),
                new Set([...X].filter(x => neighbors.has(x))),
                graph,
                cliques
            );
            P.delete(v);
            X.add(v);
        }
    }

    // Find all maximal cliques in the graph
    function findCliques(graph) {
        const cliques = [];
        const nodes = new Set(Object.keys(graph));
        bronKerbosch(new Set(), nodes, new Set(), graph, cliques);
        return cliques;
    }

    function findNotOverlappingCliques(graph) {
        let allCliques = findCliques(graph);
        
        allCliques = allCliques.sort((a, b) => b.size - a.size); // Sort cliques by size in descending order

        // Adding individual nodes as cliques
        Object.keys(graph).forEach(node => {
            allCliques.push(new Set([node]));
        });

        const selectedCliques = [];
        const usedNodes = new Set();

        //console.log("All cliques:", allCliques);

        // Selecting non-overlapping cliques
        allCliques.forEach(clique => {
            if (![...clique].some(node => usedNodes.has(node))) {
                selectedCliques.push(clique);
                clique.forEach(node => usedNodes.add(node));
            }
        });

        return selectedCliques;
    }

    // Convert setData to a graph structure
    function fromSetsToGraph(sets) {
        const graph = {};
        sets.forEach(elem => {
            graph[elem.set_id] = elem.links;
        });
        return graph;
    }

    const graph = fromSetsToGraph(setData);
    //console.log("Graph Structure : ", graph)

    const nonOverlappingCliques = findNotOverlappingCliques(graph);
    
    console.log("Non-overlapping maximal cliques:", nonOverlappingCliques);
    console.log("Number of non-overlapping cliques:", nonOverlappingCliques.length);

    return nonOverlappingCliques.length
}