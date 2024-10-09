// Metric Name : Container Service Coverage (for each container)
// num_serv_be = numero di servizi backend
// num_clique_full = numero di clique fullfilled
// num_set_full = numero di set fullfilled
//                     if num_serv_be <= num_clique_full                  | = 100*(num_serv_be/num_clique_full)
// metric_result =     if num_clique_full < num_serv_be < num_set_full    | = 100
//                     if 2*num_set_full > num_serv_be >= num_set_full    | = 100*(2*num_set_full-num_serv_be)/num_set_full


const { getCliquesUserStories } = require("./ge")

function cscMetrics(projectData, benchmarkData) {

    let result = []

    for (const container of projectData.containers) {

        const cliques = getCliquesUserStories(benchmarkData.metrics)
        let num_set_fullfilled = 0
        let num_clique_fulfilled = 0
        let num_serv_be = 0

        for (const clique of cliques) {

            const usFulfilled = clique.user_stories.every(us => container.userStories.includes(us))

            if (usFulfilled)  num_clique_fulfilled++
        }

        for (const set of benchmarkData.metrics) {

            const usFulfilled = set.user_stories.every(us => container.userStories.includes(us))

            if (usFulfilled)  num_set_fullfilled++
        }

        for(const service of container.services){

            if (service.type == "backend") num_serv_be++

        }

        let metric_result = 0
        if ( num_clique_fulfilled === 0 || num_set_fullfilled === 0 ) metric_result = null
        else if ( num_serv_be <= num_clique_fulfilled ) metric_result = 100 * (num_serv_be/num_clique_fulfilled)
        else if ( num_clique_fulfilled <= num_serv_be && num_serv_be <= num_set_fullfilled ) metric_result = 100
        else if ( num_serv_be >= num_set_fullfilled && num_serv_be < 2*num_set_fullfilled) metric_result = 100 * (2*num_set_fullfilled-num_serv_be)/num_set_fullfilled

        result.push({ "containerName": container.name, "result": metric_result })

    }

    return result
}

module.exports = {
    cscMetrics
}