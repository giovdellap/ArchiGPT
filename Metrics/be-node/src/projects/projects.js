const testProjData = require("../projects/test/Data.json")
const testDataMetrics = require("../projects/test/DataMetrics.json")
const onesportProjData = require("../projects/OneSport/Data.json")
const onesportDataMetrics = require("../projects/OneSport/DataMetrics.json")

function getDataMetricsProject(name) {
    console.log('NAME', name)
    let projData
    switch (name) {
        case 'test':
            projData = testProjData
            projData.metrics = testDataMetrics
            return projData
        case 'OneSport':
            projData = onesportProjData
            projData.metrics = onesportDataMetrics
            return projData
        default:
            return {}
    }
}

module.exports = {
    getDataMetricsProject
}