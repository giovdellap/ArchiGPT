const onesportProjData = require("./OneSport/Data.json")
const onesportDataMetrics = require("./OneSport/DataMetrics.json")
const nffhProjData = require("./NFFH/Data.json")
const nffhDataMetrics = require("./NFFH/DataMetrics.json")
const efarmersProjData = require("./EFarmers/Data.json")
const efarmersDataMetrics = require("./EFarmers/DataMetrics.json")
const rentyourexpertProjData = require("./RentYourExpert/Data.json")
const rentyourexpertDataMetrics = require("./RentYourExpert/DataMetrics.json")
const cdcProjData = require("./CDC/Data.json")
const cdcDataMetrics = require("./CDC/DataMetrics.json")
const eventticketProjData = require("./EventTicket/Data.json")
const eventticketDataMetrics = require("./EventTicket/DataMetrics.json")
const teamifyProjData = require("./Teamify/Data.json")
const teamifyDataMetrics = require("./Teamify/DataMetrics.json")
const recipecoveProjData = require("./RecipeCove/Data.json")
const recipecoveDataMetrics = require("./RecipeCove/DataMetrics.json")

function getBenchmarkProjects(name) {
    let projData
    switch (name) {
        case 'OneSport':
            projData = onesportProjData
            projData.metrics = onesportDataMetrics
            return projData
        case 'NFFH':
            projData = nffhProjData
            projData.metrics = nffhDataMetrics
            return projData
        case 'EFarmers':
            projData = efarmersProjData
            projData.metrics = efarmersDataMetrics
            return projData
        case 'RentYourExpert':
            projData = rentyourexpertProjData
            projData.metrics = rentyourexpertDataMetrics
            return projData
        case 'CDC':
            projData = cdcProjData
            projData.metrics = cdcDataMetrics
            return projData
        case 'EventTricket':
            projData = eventticketProjData
            projData.metrics = eventticketDataMetrics
            return projData
        case 'Teamify':
            projData = teamifyProjData
            projData.metrics = teamifyDataMetrics
            return projData
        case 'RecipeCove':
            projData = recipecoveProjData
            projData.metrics = recipecoveDataMetrics
            return projData
        default:
            return {}
    }
}

module.exports = {
    getBenchmarkProjects
}