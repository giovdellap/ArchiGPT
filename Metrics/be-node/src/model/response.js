class Response {
    modelName = ""
    finalIndex = 0 //risultato totale modello
    projectsResults = [] // risultati progetti
}

class ProjectResult {
    projectName = ""
    projectIndex = 0

    ussc = 0
    cic = 0
    ge = 0
    sc = 0
    pc = 0
    scec = 0
}

module.exports = {
Response,
ProjectResult
}