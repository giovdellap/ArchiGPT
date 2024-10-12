// Metric Name : Container Persistance Coverage
// If a container contains a set with db = true, than this container must have at least a database microservice
// num_c_true = number of containers that contains a set with db = true
// num_c_true_db = number of containers that contains a set with db = true and have at least a database microservice
// (for each container, metric_result = num_c_true_db/num_c_true)


function cpcMetrics(projectData, benchmarkData) {

    let summ_result = 0
    let num_container = 0

    for (const container of projectData.containers) {

        let result = 0

        for (const set of benchmarkData.metrics) {

            const usFulfilled = set.user_stories.every(us => container.userStories.includes(us))
    
            if (usFulfilled && set.db){
                num_container++
                if (container.services.find(element => element.type === "database")){
                    result = 1
                } else result = 0
                break
            }
        }

        summ_result = summ_result + result
    }

    console.log("Number of container that fullfill a set and have db = true : ", num_container)

    if (num_container === 0) {
        return 0
    } else return (100 * (summ_result/num_container))
}

module.exports = {
    cpcMetrics
}