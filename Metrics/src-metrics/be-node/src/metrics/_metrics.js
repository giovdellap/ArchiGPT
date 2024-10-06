const { scecMetrics } = require("./scec");
const { cecMetrics } = require("./cec");
const { cpcMetrics } = require("./cpc");
const { scMetrics } = require("./sc");
const { cscMetrics } = require("./csc");
const { geMetrics } = require("./ge");
const { cicMetrics } = require("./cic");
const { usscMetrics } = require("./ussc");

const metrics = ['ussc', 'cic', 'ge', 'csc', 'sc', 'cpc','cec', 'scec']
const finalMetrics = ['ussc', 'cic', 'ge', 'sc', 'cpc', 'scec']

function getMetricResult(name, projectData, benchmarkData, projResult) {
    switch (name) {
        case metrics[0]:
            return usscMetrics(projectData, benchmarkData)
        case metrics[1]:
            return cicMetrics(projectData, benchmarkData)
        case metrics[2]:
            return geMetrics(projectData, benchmarkData)
        case metrics[3]:
            return cscMetrics(projectData, benchmarkData)
        case metrics[4]:
            return scMetrics(projectData, projResult)
        case metrics[5]:
            return cpcMetrics(projectData, benchmarkData)
        case metrics[6]:
            return cecMetrics(projectData)
        case metrics[7]:
            return scecMetrics(projectData, projResult)
        default:
            return {}
    }
}

module.exports = {
    metrics,
    finalMetrics,
    getMetricResult
}