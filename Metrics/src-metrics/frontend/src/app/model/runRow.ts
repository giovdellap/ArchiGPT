import { FinalResults, RunResult } from "./benchmarkresult"
import { finalMetrics, finalMetricsNames } from "./finalMetricsCard"
import { getProjectRows, ProjectRow } from "./projectRow"
import { rgbToHex } from "./utils"

export class RunRow {
    name: string = ""
    index: number = 0
    color: string = ""
    metricCards: RunMetricCard[] = []
    projectRows: ProjectRow[] = []
}

export class RunMetricCard {
    metric: string = ""
    index: number = 0
    color: string = ""
}

export function getRunRows(runsResults: RunResult[]): RunRow[] {
    let res: RunRow[] = []

    for (const run of runsResults) {
        let cards: RunMetricCard[] = []
        for (let i = 0; i < finalMetrics.length; i++) {
            let index = Number((<number>run.runFinalResults[finalMetrics[i] as keyof FinalResults]).toFixed(2))
            let color = rgbToHex(
                Number((Math.min(255, Math.max(0, 255 * ((100 - index) / 100)))).toFixed(0)),
                Number(Math.min(255, Math.max(0, 255 * (index / 100))).toFixed(0)),
                0
            )
            console.log(color)
            cards.push({
                metric: finalMetricsNames[i],
                index: index,
                color: color
            })
        }
        let indexColor = rgbToHex(
            Number((Math.min(255, Math.max(0, 255 * ((100 - run.runFinalIndex) / 100)))).toFixed(0)),
            Number(Math.min(255, Math.max(0, 255 * (run.runFinalIndex / 100))).toFixed(0)),
            0
        )
        res.push({
            name: 'RUN ' + (run.runIndex + 1),
            index: run.runFinalIndex,
            color: indexColor,
            metricCards: cards,
            projectRows: getProjectRows(run.projects)
        })
    }

    return res
} 