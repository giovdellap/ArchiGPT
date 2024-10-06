import { ProjectResult } from "./benchmarkresult"
import { finalMetrics, finalMetricsNames } from "./finalMetricsCard"
import { rgbToHex } from "./utils"

export class ProjectRow {
    name: string = ""
    index: number = 0
    metricCards: ProjectMetricCard[] = []
}

export class ProjectMetricCard {
    metric: string = ""
    index: number = 0
    color: string = ""
}

export function getProjectRows(projectsResults: ProjectResult[]): ProjectRow[] {
    let res: ProjectRow[] = []

    for (const proj of projectsResults) {
        let cards: ProjectMetricCard[] = []
        for (let i = 0; i < finalMetrics.length; i++) {
            let index = Number((<number>proj[finalMetrics[i] as keyof ProjectResult]).toFixed(2))
            let color = rgbToHex(
                Math.min(255, Math.max(0, 255 * ((100 - index) / 100))),
                Math.min(255, Math.max(0, 255 * (index / 100))),
                0
            )
            cards.push({
                metric: finalMetricsNames[i],
                index: index,
                color: color
            })
        }
        res.push({
            name: proj.projectName,
            index: proj.projectIndex,
            metricCards: cards
        })
    }

    return res
} 