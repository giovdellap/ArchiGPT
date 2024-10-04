// Metric Name : Container Integrity Coverage
// num_set_us = set di user stories completi
// metric_result = 100*(num_set_us/num_set)

function cicMetrics(project, benchmarkData) {

    const n_set = benchmarkData.metrics.length
    let num_set_us = 0
    //Miss set links
    for (const set of benchmarkData.metrics) {
        let setComplete = false
        for (const container of project.containers) {
            let usFulfilled = 0
            //console.log(container.userStories)
            for (const us of set.user_stories) {
                if(container.userStories.includes(us)) usFulfilled++
            }
            if (usFulfilled === set.user_stories.length) setComplete = true
        }
        if (setComplete) n_ss++
    }

    const result = 100 * (num_set_us/n_set)
    return result
}

module.exports = {
    cicMetrics
}