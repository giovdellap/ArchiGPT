const { cicMetrics } = require("./cic");
const { usscMetrics } = require("./ussc");

const metrics = ['ussc', 'cic']

function getMetricResult(name, project, data) {
    switch (name) {
        case metrics[0]:
            return usscMetrics(project, data)
        case metrics [1]:
            return cicMetrics(project, data)
        default:
            return {}
    }
}

module.exports = {
    metrics,
    getMetricResult
}