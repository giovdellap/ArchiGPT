import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private logged: boolean = false;
  private token: string = "";
  private header: HttpHeaders = new HttpHeaders();
  private id: string = "";
  private email: string = "";
  private username: string = "";

  subject: BehaviorSubject<boolean> = new BehaviorSubject(this.logged);

  constructor(private cookie: CookieService) { }

  setUser(
    token : string, 
    id: string, 
    email: string, 
    username: string) {

    this.id = id;
    this.email = email;
    this.username = username;
    this.token = token;
    this.logged = true;
    this.header = new HttpHeaders({token: token});

    //this.cookie.set('NFFH_Client_id', id)
    //this.cookie.set('NFFH_Client_token', token)
    //this.cookie.set('NFFH_Client_email', email)
    //this.cookie.set('NFFH_Client_username', username)
    
    this.subject.next(this.logged);

  }

  getObservable() {
    return this.subject as Observable<boolean>;
  }

  getHeader(): HttpHeaders {
    return this.header;
  }

  getToken() {
    return this.token;
  }

  getId(): string {
    return this.id
  }

  getEmail(): string {
    return this.email;
  }

  getUsername(): string {
    return this.username;
  }
  
}
