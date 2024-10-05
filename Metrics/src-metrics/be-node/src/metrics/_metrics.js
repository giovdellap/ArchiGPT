const { geMetrics } = require("./ge");
const { cicMetrics } = require("./cic");
const { usscMetrics } = require("./ussc");

const metrics = ['ussc', 'cic', 'ge']

function getMetricResult(name, projectData, benchmarkData) {
    switch (name) {
        case metrics[0]:
            return usscMetrics(projectData, benchmarkData)
        case metrics [1]:
            return cicMetrics(projectData, benchmarkData)
        case metrics [2]:
            return geMetrics(projectData, benchmarkData)
        default:
            return {}
    }
}

module.exports = {
    metrics,
    getMetricResult
}