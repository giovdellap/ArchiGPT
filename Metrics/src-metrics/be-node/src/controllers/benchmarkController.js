const { BenchmarkResult, ProjectResult, RunResults, RunResult } = require("../model/response")
const { calculateFinalResults, calculateIndex, calculateProjectsResults, calculateTotalResults } = require("./utils")


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

  
module.exports = {
  benchmark
}
  