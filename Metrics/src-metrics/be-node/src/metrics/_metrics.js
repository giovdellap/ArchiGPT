const { cscMetrics } = require("./csc");
const { geMetrics } = require("./ge");
const { cicMetrics } = require("./cic");
const { usscMetrics } = require("./ussc");

const metrics = ['ussc', 'cic', 'ge', 'csc']

function getMetricResult(name, projectData, benchmarkData) {
    switch (name) {
        case metrics[0]:
            return usscMetrics(projectData, benchmarkData)
        case metrics [1]:
            return cicMetrics(projectData, benchmarkData)
        case metrics [2]:
            return geMetrics(projectData, benchmarkData)
        case metrics [3]:
            return cscMetrics(projectData, benchmarkData)
        default:
            return {}
    }
}

module.exports = {
    metrics,
    getMetricResult
}