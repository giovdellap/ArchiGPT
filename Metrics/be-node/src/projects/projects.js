const testProjData = require("../projects/test/Data.json")
const testDataMetrics = require("../projects/test/DataMetrics.json")

function getDataMetricsProject(name) {
    switch (name) {
        case 'test':
            let projData = testProjData
            projData.metrics = testDataMetrics
            return projData
        default:
            return {}
    }
}

module.exports = {
    getDataMetricsProject
}