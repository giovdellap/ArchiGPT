// Metric Name : User Stories Satisfaction Coverage
// us_sod = user stories soddisfatte
// metric_result = 100 *(us_sod/num_us_tot)

function usscMetrics(projectData, benchmarkData) {

    const num_us_tot = benchmarkData.userStories
    let us_sod = []

    for (const container of projectData.containers) {
        for (const service of container.services) {
            if (service.type === 'backend' && service.endpoints) {
                for (const endpoint of service.endpoints) {
                    us_sod.push(...endpoint.userStoryIndex)
                    //console.log('USER STORY FULFILLED BY ENDPOINT', ...endpoint.userStoryIndex, service.name)
                }
            }
            if (service.type === 'frontend' && service.pages) {
                for (const page of service.pages) {
                    us_sod.push(...page.userStoryIndex)
                    //console.log('USER STORY FULFILLED BY PAGE', ...page.userStoryIndex, service.name)
                }
            }
        }
    }

    us_sod = [...new Set(us_sod)]       // Remove User Stories duplicated
    console.log("User Stories Satisfied : ", us_sod)

    const result = 100 * (us_sod.length/num_us_tot)
    return result
}

module.exports = {
    usscMetrics
}