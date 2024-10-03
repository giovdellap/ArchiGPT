function usscMetrics(project, data) {
    console.log('USSC METRIC')
    const n_us = data.userStories
    console.log('N_US', n_us)
    let n_sod = 0
    for (let usIndex = 1; usIndex <= n_us; usIndex++) {
        let fulfilled = false
        for (const container of project.containers) {
            for (const service of container.services) {
                if (service.type === 'backend') {
                    for (const endpoint of service.endpoints) {
                        if(endpoint.userStoryIndex.includes(usIndex)) {
                            fulfilled = true
                        }
                    }
                }
            }
        }
        if (fulfilled) n_sod++
    }

    console.log('N_SOD', n_sod)
    const result = 100 * (n_sod/n_us)
    return result
}

module.exports = {
    usscMetrics
}