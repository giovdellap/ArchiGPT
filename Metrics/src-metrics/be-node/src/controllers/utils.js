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

module.exports = {
    calculateFinalResults
}