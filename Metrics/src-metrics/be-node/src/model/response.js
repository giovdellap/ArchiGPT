class BenchmarkResult {
    modelName = ""

    finalIndex = 0 //risultato totale modello
    finalResults = new FinalResults() //medie metriche

    projectsResults = [] // risultati progetti
}

class FinalResults {
    ussc = 0
    cic = 0
    ge = 0
    sc = 0
    pc = 0
    scec = 0
}

class ProjectResult {
    projectName = ""
    projectIndex = 0

    ussc = 0
    cic = 0
    ge = 0
    csc = []
    sc = 0
    pc = 0
    scec = 0
}

module.exports = {
BenchmarkResult,
ProjectResult,
FinalResults
}