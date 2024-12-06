const { finalMetrics, metrics, getMetricResult } = require("../metrics/_metrics");
const { FinalResults, ProjectResult } = require("../model/response");
const { getBenchmarkProjects } = require("../data/benchmarkProjects/projects")

function calculateFinalResults(projectResults) {
    let finalResults = new FinalResults()
    for(const metricName of finalMetrics) {
        let sum = 0
        for (const projResult of projectResults) {
            sum = sum + projResult[metricName]
        }
        finalResults[metricName] = sum/projectResults.length
    }
    return finalResults
}

function calculateIndex(results) {
    let sum = results.ussc + results.cic + results.ge + results.sc + results.cpc + results.scec
    return Number((sum/6).toFixed(2))
}

function calculateProjectsResults(runProjects) {
    let results = []

    for (const proj of runProjects) {
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
        results.push(projResult)
    }

    return results
}

function calculateTotalResults(runResults) {
    let totalResults = new FinalResults()
    for (const key of Object.keys(totalResults)) {
        console.log('KEY', key)
        let sum = 0
        for (const run of runResults) {
            sum = sum + run.runFinalResults[key]
        }
        totalResults[key] = sum/runResults.length
    }
    return totalResults
}

function calculateProjectsTotalResults(runResults) {
    let totalResults = new FinalResults()
    for (const key of Object.keys(totalResults)) {
        console.log('KEY', key)
        let sum = 0
        for (const run of runResults) {
            sum = sum + run.projectFinalResults[key]
        }
        totalResults[key] = sum/runResults.length
    }
    return totalResults
}

const projectNames = ["OneSport", "NFFH", "CDC", "EFarmers", "EventTicket", "RecipeCove", "RentYourExpert", "Teamify"]

module.exports = {
    calculateFinalResults,
    calculateIndex,
    calculateProjectsResults,
    calculateTotalResults,
    projectNames,
    calculateProjectsTotalResults
}