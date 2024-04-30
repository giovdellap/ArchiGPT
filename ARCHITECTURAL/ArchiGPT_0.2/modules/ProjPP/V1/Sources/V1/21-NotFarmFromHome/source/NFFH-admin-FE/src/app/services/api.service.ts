import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, switchMap, tap } from 'rxjs';
import { AreasItemResponse } from '../model/area';
import { LoginRequest, LoginResponse } from '../model/login';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private url = "http://localhost:8080"
  private header: HttpHeaders = new HttpHeaders()

  constructor(private http: HttpClient) { }

  login(email: string, password: string) {
    const req: LoginRequest = {
      email: email,
      password: password
    }
    return this.http.post<LoginResponse>(this.url+'/admin/login', req).pipe(
      tap(x => this.header = new HttpHeaders({token: x.token}))
    )
  }

  getAreas(): Observable<string[]> {
    return this.http.get<AreasItemResponse[]>(this.url+'/area').pipe(
      switchMap(x => {
        var a: string[] = []
        x.forEach(area => a.push(area.areaName))
        return of(a)
      })
    )
  }

  newArea(name: string) {
    return this.http.post(this.url+"/area", {areaName: name}, {headers: this.header})
  }
}
