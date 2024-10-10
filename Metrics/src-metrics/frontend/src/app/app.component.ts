import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
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
import { getProjectRows, ProjectRow } from './model/projectRow';
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
    MatExpansionModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
  finalMetrics = finalMetrics

  status = "notready"
  statusBS = new BehaviorSubject<string>(this.status)
  statusObs = this.statusBS.asObservable()

  currentRequest: Request = {} as Request
  result: BenchmarkResult = {} as BenchmarkResult
  resultColor: string = ""
  finalMetricsCards: FinalMetricsCard[] = []
  projectRows: ProjectRow[] = []


  constructor(private api: ApiService) { }

  onJsonAdded(event: any) {
    const file: File = event.target.files[0];
    file.text().then((txt: string) => this.currentRequest = JSON.parse(txt))
    this.status = "ready"
    this.statusBS.next(this.status)
  }

  onStart() {
    this.status = "waiting"
    this.statusBS.next(this.status)
    this.api.benchmark(this.currentRequest).subscribe((res: BenchmarkResult) => {
      this.result = res
      this.finalMetricsCards = getFinalMetricsCards(this.result.finalResults)
      this.calculatefinalIndexColor(this.result.finalIndex)
      this.projectRows = getProjectRows(this.result.projectsResults)
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
