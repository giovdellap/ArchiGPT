const { BenchmarkResult, ProjectResult, RunResults, RunResult, ProjectBenchmarkResult, ProjectsResponse } = require("../model/response")
const { calculateFinalResults, calculateIndex, calculateProjectsResults, calculateTotalResults, projectNames, calculateProjectsTotalResults } = require("./utils")


const benchmark = ( async (req, res) => {

  console.log("REQ BODY : ", req.body)

  const runs = req.body.runs

  let benchmark = new BenchmarkResult()
  benchmark.modelName = req.body.modelName

  for (let i = 0; i < runs.length; i++) {
    let runresult = new RunResult()
    runresult.runIndex = i
    runresult.projects = calculateProjectsResults(runs[i].projects)
    runresult.runFinalResults = calculateFinalResults(runresult.projects)
    runresult.runFinalIndex = calculateIndex(runresult.runFinalResults)
    benchmark.runsResults.push(runresult)
  }
  

  benchmark.finalResults = calculateTotalResults(benchmark.runsResults)  
  benchmark.finalIndex = calculateIndex(benchmark.finalResults)

  console.log("Benchmark Final Result completed for all the projects")

  res.json(benchmark)

})

const projBenchmark = ( async (req, res) => {

  console.log("REQ BODY : ", req.body)

  const runs = req.body.runs

  let projectsResponse = new ProjectsResponse()
  projectsResponse.modelName = req.body.modelName

  let benchmark = new BenchmarkResult()
  benchmark.modelName = req.body.modelName

  for (let i = 0; i < runs.length; i++) {
    let runresult = new RunResult()
    runresult.runIndex = i
    runresult.projects = calculateProjectsResults(runs[i].projects)
    benchmark.runsResults.push(runresult)
  }
  
  for (const name of projectNames) {
    let projBenchmark = new ProjectBenchmarkResult()
    projBenchmark.projectName = name
    for (let runIndex = 0; runIndex < benchmark.runsResults.length; runIndex ++) {
      for (let projectIndex = 0; projectIndex < benchmark.runsResults[runIndex].projects.length; projectIndex++) {
        if (benchmark.runsResults[runIndex].projects[projectIndex].projectName === name) {
          projBenchmark.projectRuns.push(benchmark.runsResults[runIndex].projects[projectIndex])
        }
      }
    }
    projBenchmark.projectFinalResults = calculateFinalResults(projBenchmark.projectRuns)
    projBenchmark.projectFinalIndex = calculateIndex(projBenchmark.projectFinalResults)
    projectsResponse.projectsResults.push(projBenchmark)
  }

  projectsResponse.finalResults = calculateProjectsTotalResults(projectsResponse.projectsResults)
  projectsResponse.finalIndex = calculateIndex(projectsResponse.finalResults)

  console.log("Benchmark Final Result completed for all the projects")

  res.json(projectsResponse)

})

  
module.exports = {
  benchmark,
  projBenchmark
}
  