import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterOutlet } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { BenchmarkResult } from './model/benchmarkresult';
import { finalMetrics, FinalMetricsCard, getFinalMetricsCards } from './model/finalMetricsCard';
import { MetricRequest } from './model/request';
import { getRunRows, RunRow } from './model/runRow';
import { rgbToHex } from './model/utils';
import { ApiService } from './services/api.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    CommonModule,
    MatToolbarModule,
    MatCardModule,
    MatExpansionModule,
    ReactiveFormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
  inputRows = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
  finalMetrics = finalMetrics

  status = "notready"
  statusBS = new BehaviorSubject<string>(this.status)
  statusObs = this.statusBS.asObservable()

  currentRequest: MetricRequest = new MetricRequest()
  nameFC: FormControl = new FormControl('')

  result: BenchmarkResult = {} as BenchmarkResult
  resultColor: string = ""
  finalMetricsCards: FinalMetricsCard[] = []
  runRows: RunRow[] = []


  constructor(private api: ApiService) { }

  onJsonAdded(event: any) {
    const file: File = event.target.files[0];
    file.text().then((txt: string) => this.currentRequest.runs.push(JSON.parse(txt)))
    this.status = "ready"
    this.statusBS.next(this.status)
  }


  onStart() {
    this.currentRequest.modelName = this.nameFC.value
    this.status = "waiting"
    this.statusBS.next(this.status)
    this.api.benchmark(this.currentRequest).subscribe((res: BenchmarkResult) => {
      this.result = res
      this.finalMetricsCards = getFinalMetricsCards(this.result.finalResults)
      this.calculatefinalIndexColor(this.result.finalIndex)
      this.runRows = getRunRows(this.result.runsResults)
      this.status = "result"
      this.statusBS.next(this.status)
      console.log(this.result)
    })
  }

  onProjStart() {
    this.currentRequest.modelName = this.nameFC.value
    this.status = "waiting"
    this.statusBS.next(this.status)
    this.api.projBenchmark(this.currentRequest).subscribe((res: BenchmarkResult) => {
      this.result = res
      this.finalMetricsCards = getFinalMetricsCards(this.result.finalResults)
      this.calculatefinalIndexColor(this.result.finalIndex)
      this.runRows = getRunRows(this.result.runsResults)
      this.status = "result"
      this.statusBS.next(this.status)
      console.log(this.result)
    })
  }

  calculatefinalIndexColor(index: number) {
    this.resultColor = rgbToHex(
      Number((Math.min(255, Math.max(0, 255 * ((100 - index) / 100)))).toFixed(0)),
      Number(Math.min(255, Math.max(0, 255 * (index / 100))).toFixed(0)),
      0
  )
  }


}
