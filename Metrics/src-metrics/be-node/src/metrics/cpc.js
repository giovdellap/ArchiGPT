// Metric Name : Container Persistance Coverage
// If a container contains a set with db = true, than this container must have at least a database microservice

function cpcMetrics(projectData, benchmarkData) {

    let summ_result = 0
    num_container = 0

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
    if (num_container === 0) {
        return 0
    } else return (100 * (summ_result/num_container))
}

module.exports = {
    cpcMetrics
}