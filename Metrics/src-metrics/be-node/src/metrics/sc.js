// Metric Name : Service Coverage
// metric_result = sommatoria di (for each container, (1/n_c)*sc)

function scMetrics(projectData, projResult) {

    cscResults = projResult["csc"]
    sum_css_result = 0
    num_container = projectData.containers.length

    for (const container of cscResults) {
        if(container.result != null){ 
            sum_css_result = sum_css_result + container.result
        } else {
            num_container--
        }
    }

    const result = sum_css_result/num_container
    return result
}

module.exports = {
    scMetrics
}