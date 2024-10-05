const { cpcMetrics } = require("./cpc");
const { scMetrics } = require("./sc");
const { cscMetrics } = require("./csc");
const { geMetrics } = require("./ge");
const { cicMetrics } = require("./cic");
const { usscMetrics } = require("./ussc");

const metrics = ['ussc', 'cic', 'ge', 'csc', 'sc', 'cpc']

function getMetricResult(name, projectData, benchmarkData, projResult) {
    switch (name) {
        case metrics[0]:
            return usscMetrics(projectData, benchmarkData)
        case metrics [1]:
            return cicMetrics(projectData, benchmarkData)
        case metrics [2]:
            return geMetrics(projectData, benchmarkData)
        case metrics [3]:
            return cscMetrics(projectData, benchmarkData)
        case metrics [4]:
            return scMetrics(projectData, projResult)
        case metrics [5]:
            return cpcMetrics(projectData, benchmarkData)
        default:
            return {}
    }
}

module.exports = {
    metrics,
    getMetricResult
}