import { ProjectResult } from "./benchmarkresult"
import { finalMetrics, finalMetricsNames } from "./finalMetricsCard"
import { rgbToHex } from "./utils"

export class ProjectRow {
    name: string = ""
    index: number = 0
    color: string = ""
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
            Number((Math.min(255, Math.max(0, 255 * ((100 - proj.projectIndex) / 100)))).toFixed(0)),
            Number(Math.min(255, Math.max(0, 255 * (proj.projectIndex / 100))).toFixed(0)),
            0
        )
        res.push({
            name: proj.projectName,
            index: proj.projectIndex,
            color: indexColor,
            metricCards: cards
        })
    }

    return res
} 