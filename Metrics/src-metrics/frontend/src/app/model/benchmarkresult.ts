export class BenchmarkResult {
    modelName: string = ""
    finalIndex: number = 0 //risultato totale modello
    finalResults: FinalResults = new FinalResults()
    projectsResults: ProjectResult[] = [] // risultati progetti
}

export class FinalResults {
    ussc: number = 0
    cic: number = 0
    ge: number = 0
    sc: number = 0
    pc: number = 0
    scec: number = 0
}

export class ProjectResult {
    projectName: string = ""
    projectIndex: number = 0

    ussc: number = 0
    cic: number = 0
    ge: number = 0
    csc: any[] = []
    sc: number = 0
    pc: number = 0
    scec: number = 0
}