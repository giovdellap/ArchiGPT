function cicMetrics(project, data) {
    const n_set = data.metrics.length
    let n_ss = 0
    for (const set of data.metrics) {
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

    const result = 100 * (n_ss/n_set)
    return result
}

module.exports = {
    cicMetrics
}