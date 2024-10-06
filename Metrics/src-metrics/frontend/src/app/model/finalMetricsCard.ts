import { FinalResults } from "./benchmarkresult"
import { rgbToHex } from "./utils"

export class FinalMetricsCard {
    metricAcronym: string
    metricName: string
    metricIndex: number
    color: string

    constructor(acronym: string, name: string, index: number, color: string) {
        this.metricAcronym = acronym
        this.metricName = name
        this.metricIndex = index
        this.color = color
    }
}

export const metrics: string[] = ['ussc', 'cic', 'ge', 'csc', 'sc', 'cpc', 'pc', 'cec', 'scec']
export const metricsNames: string[] = [
    'User Stories Satisfaction Coverage',
    'Container Integrity Coverage',
    'Granularity Evaluation',
    'Container Service Coverage',
    'Service Coverage',
    'Container Persistance Coverage',
    'Persistance Coverage',
    'Container Endpoint Coverage',
    'System Container Endpoint Coverage'
]

export const finalMetrics: string[] = ['ussc', 'cic', 'ge', 'sc', 'pc', 'scec']
export const finalMetricsNames: string[] =[
    'User Stories Satisfaction Coverage',
    'Container Integrity Coverage',
    'Granularity Evaluation',
    'Service Coverage',
    'Persistance Coverage',
    'System Container Endpoint Coverage'
]



export function getFinalMetricsCards(finalResults: FinalResults): FinalMetricsCard[] {
    let res: FinalMetricsCard[] = []
    for (let i = 0; i < finalMetrics.length; i++) {
        let index = Number(finalResults[finalMetrics[i] as keyof FinalResults].toFixed(2))
        let color = rgbToHex(
            Number((Math.min(255, Math.max(0, 255 * ((100 - index) / 100)))).toFixed(0)),
            Number(Math.min(255, Math.max(0, 255 * (index / 100))).toFixed(0)),
            0
        )
        console.log(color)
        let card = new FinalMetricsCard(finalMetrics[i], finalMetricsNames[i], index, color)
        res.push(card)
    }
    return res
}