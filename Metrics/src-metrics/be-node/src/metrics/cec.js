// Metric Name : Container Endpoint Coverage (for each container)
// num_us_c = numero user stories container c
// num_us_e = numero user stories coperte da un endpoint e
// metric_result = 100*((sommatoria di num_us_e)/num_us_c)

function cecMetrics(projectData) {

    let metric_result = []

    for (const container of projectData.containers) {

        const num_us_c = container.userStories.length
        let us_c = [...container.userStories]

        for (const service of container.services) {

            if(service.type == "backend"){
                for (const endpoint of service.endpoints) {

                    endpoint.userStoryIndex.forEach(us => {
                        const fullfilled = us_c.find(element => us === element) 
                        if(fullfilled)  us_c = us_c.filter(story => story !== us)
                    });
                }
            }

            if(service.type == "frontend" && service.pages){
                
                for (const page of service.pages) {

                    page.userStoryIndex.forEach(us => {
                        const fullfilled = us_c.find(element => us === element) 
                        if(fullfilled) us_c = us_c.filter(story => story !== us)
                    });
                }
            }
        }

        if(num_us_c == 0) result = null
        else result = 100*((num_us_c - us_c.length) / num_us_c)
        metric_result.push({ "containerName": container.name , "result": result})
    }

    return metric_result
}

module.exports = {
    cecMetrics
}