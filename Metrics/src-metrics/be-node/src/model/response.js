class BenchmarkResult {
    modelName = ""

    finalIndex = 0 //risultato totale modello
    finalResults = new FinalResults() //medie metriche

    runsResults = [] // risultati progetti
}

class FinalResults {
    ussc = 0
    cic = 0
    ge = 0
    sc = 0
    cpc = 0
    scec = 0
}

class RunResult{
    runIndex = 0
    runFinalIndex = 0

    runFinalResults = {}

    projects = []
}

class ProjectResult extends FinalResults{
    projectName = ""
    projectIndex = 0

    csc = []
    cec = []
}

module.exports = {
BenchmarkResult,
ProjectResult,
FinalResults,
RunResult
}