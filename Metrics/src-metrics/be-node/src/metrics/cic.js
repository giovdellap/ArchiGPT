// Metric Name : Container Integrity Coverage
// num_set_us = set di user stories completi
// metric_result = 100*(num_set_us/num_set)

function cicMetrics(projectData, benchmarkData) {

    const num_set = benchmarkData.metrics.length
    let num_set_us = 0

    for (const set of benchmarkData.metrics) {
        let setComplete = false

        for (const container of projectData.containers) {

            const usFulfilled = set.user_stories.every(us => container.userStories.includes(us))

            if (usFulfilled){
                setComplete = true
                break
            } 
        }

        if (setComplete) num_set_us++
    }

    const result = 100 * (num_set_us/num_set)
    return result
}

module.exports = {
    cicMetrics
}