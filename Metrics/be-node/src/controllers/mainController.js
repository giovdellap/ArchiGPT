const { metrics, getMetricResult } = require("../metrics/metrics")
const { Response, ProjectResult } = require("../model/response")
const { getDataMetricsProject } = require("../projects/projects")


const start = ( async (req, res) => {

  console.log("REQ BODY", req.body)

  const reqProjects = req.body.projects

  let response = new Response()
  response.modelName = req.body.modelName

  for (const proj of reqProjects) {
    let projResult = new ProjectResult()
    projResult.projectName = proj.name

    for (const metric of metrics) {
      const dataMetrics = getDataMetricsProject(proj.name)
      const metricRes = getMetricResult(metric, proj, dataMetrics)
      projResult[metric] = metricRes
      console.log(proj.name, 'METRIC', metric)
      console.log(proj.name, 'METRIC RESULT', metricRes)
    }

    response.projectsResults.push(projResult)
  }


  res.json(response)

})

  
module.exports = {
  start
}
  