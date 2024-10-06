// Metric Name : System Container Endpoint Coverage 
// metric_result = sommatoria di cec*(1/n_c) (For each container)

function scecMetrics(projectData, projResult) {

    cecResults = projResult["cec"]
    sum_cec_result = 0
    num_container = projectData.containers.length

    console.log(cecResults)

    for (const container of cecResults) {
        if(container.result != null){ 
            sum_cec_result = sum_cec_result + container.result
        } else {
            num_container--
        }
        console.log(sum_cec_result, container.result)
    }

    const result = sum_cec_result/num_container
    return result
}

module.exports = {
    scecMetrics
}