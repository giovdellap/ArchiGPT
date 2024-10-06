import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { BenchmarkResult } from '../model/benchmarkresult';

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

  benchmark(request: Request) {
    return this.getLogObservable(
      this.http.post<BenchmarkResult>(this.url + "/metrics/benchmark", request)
    )
  }


}
