const { cicMetrics } = require("./cic");
const { usscMetrics } = require("./ussc");

const metrics = ['ussc', 'cic']

function getMetricResult(name, project, benchmarkData) {
    switch (name) {
        case metrics[0]:
            return usscMetrics(project, benchmarkData)
        case metrics [1]:
            return cicMetrics(project, benchmarkData)
        default:
            return {}
    }
}

module.exports = {
    metrics,
    getMetricResult
}