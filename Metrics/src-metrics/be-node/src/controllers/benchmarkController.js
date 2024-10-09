const { metrics, getMetricResult } = require("../metrics/_metrics")
const { BenchmarkResult, ProjectResult } = require("../model/response")
const { getBenchmarkProjects } = require("../data/benchmarkProjects/projects")
const { calculateFinalResults, calculateIndex } = require("./utils")


const benchmark = ( async (req, res) => {

  console.log("REQ BODY : ", req.body)

  const reqProjects = req.body.projects

  let benchmark = new BenchmarkResult()
  benchmark.modelName = req.body.modelName


  for (const proj of reqProjects) {
    let projResult = new ProjectResult()
    projResult.projectName = proj.name
    console.log('Project Name : ', proj.name)

    for (const metric of metrics) {

      let benchmarkProjects = {}

      benchmarkProjects = getBenchmarkProjects(proj.name)

      const metricResult = getMetricResult(metric, proj, benchmarkProjects, projResult)
      projResult[metric] = metricResult
      console.log('Metric : ', metric, ' Result : ', metricResult)
    }
    let index = calculateIndex(projResult)
    projResult.projectIndex = index

    console.log("Benchmark Estimation completed for project : ", proj.name)
    benchmark.projectsResults.push(projResult)
  }

  benchmark.finalResults = calculateFinalResults(benchmark.projectsResults)  
  benchmark.finalIndex = calculateIndex(benchmark.finalResults)

  console.log("Benchmark Final Result completed for all the projects")

  res.json(benchmark)

})

  
module.exports = {
  benchmark
}
  