const { finalMetrics } = require("../metrics/_metrics");
const { FinalResults } = require("../model/response");

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

module.exports = {
    calculateFinalResults,
    calculateIndex
}