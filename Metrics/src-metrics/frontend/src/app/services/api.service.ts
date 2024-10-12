import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { BenchmarkResult } from '../model/benchmarkresult';
import { MetricRequest } from '../model/request';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private url = "http://localhost:5002"

  constructor(private http: HttpClient) { }

  getLogObservable(request: Observable<any>) {
    return request.pipe(tap((response: any) => {
      console.log('API RESPONSE: ', response)
    }))
  }

  benchmark(request: MetricRequest) {
    //const blob = new Blob([JSON.stringify(request)], { type : 'application/json' })
    //const myFile = new File([blob], 'all.json', {
      //type: blob.type,
  //});
    return this.getLogObservable(
      this.http.post<BenchmarkResult>(this.url + "/metrics/benchmark", request)
    )
  }


}
