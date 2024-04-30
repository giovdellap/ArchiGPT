import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, forkJoin, map, mergeMap, of, switchMap, tap } from 'rxjs';
import { Areas, AreasResponse, GetProductsResponse, ImageRequest, ImageResponse, LoginRequest, LoginResponse, ProductRequest, ProductResponse, SignUpRequest, SignupResponse } from '../model/connectionModel';
import { Product } from '../model/product';
import { areasList, imageResponse, mockUser, productResponse, productsResponse } from '../utils/mock';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  /** SERVICE MODE
   * 1 : API (connected to server)
   * 2: Mock
   */
  serviceMode = 1;
  url = "http://localhost:8080";

  constructor(private http: HttpClient, private user: UserService) { }

  login(email: string, password: string) {
    if (this.serviceMode == 1) {
      const req: LoginRequest = {
        email: email,
        password: password
      }
      return this.http.post<LoginResponse>(this.url+'/farmer/login', req).pipe(
        tap(x => this.user.setUser(x.token, x.id, email, x.username))
      )
    }
    else {
      this.user.setUser(mockUser.token, "1", email, "aaaaa")
      return new Observable<LoginResponse>(observer => {
        observer.next(mockUser);
        observer.complete();
      })
    }
  }

  register(req: SignUpRequest) {
    if (this.serviceMode == 1) {
      console.log('in api', req)
      return this.http.post<SignupResponse>(this.url+'/farmer/signup', req).pipe(
        tap(x => this.user.setUser(x.token, x.id, req.email, req.username)),
        tap(x => console.log('api res', x))
      )
    }
    else {
      this.user.setUser(mockUser.token, "1", req.email, req.username)
      return new Observable<LoginResponse>(observer => {
        observer.next(mockUser);
        observer.complete();
      })
    }
  }

  getLocationsList() {
    if(this.serviceMode == 1) {
      return this.http.get<AreasResponse[]>(this.url+'/area').pipe(
        map( x => {
          var res: string[] = []
          x.forEach(element => {
            res.push(element.areaName)
          });
          return {
            areas: res
          }
        })
      );
    } else {
      return new Observable<Areas>(observer => {
        observer.next(areasList);
        observer.complete();
      })
    }
  }

  sendImage(file: File): Observable<ImageResponse> {
    if (this.serviceMode == 1) {

      var subject = new Subject<ImageRequest>();
      const reader = new FileReader();
      reader.readAsDataURL(file);
      var req;

      reader.onload = (event: any) => {
        req = {
          base64_image: event.target.result,
        }
        subject.next(req)
      }
      return subject.pipe(
        mergeMap(x => this.http.post<ImageResponse>(this.url+"/images/farmer/upload", x))
      )
    } else {
      return new Observable<ImageResponse>(observer => {
        observer.next(imageResponse);
        observer.complete();
      })
    }
  }

  addProduct(product: ProductRequest) {
    if(this.serviceMode == 1) {
      return this.http.post<ProductResponse>(this.url + "/product/add",
        product,
        {headers: this.user.getHeader()}
      )
    } else {
      return new Observable<ProductResponse>(observer => {
        observer.next(productResponse);
        observer.complete();
      })
    }
  }

  modifyProduct(product: Product) {

    if(this.serviceMode == 1) {
      return this.http.post<ProductResponse>(
        this.url + "/product/modify/"+ product.id,
        product,
        {headers: this.user.getHeader()}
      )
    } else {
      return new Observable<ProductResponse>(observer => {
        observer.next(productResponse);
        observer.complete();
      })
    }
  }

  getAllProducts(): Observable<Product[]> {
    if(this.serviceMode == 1) {

      var toReturn: Product[] = []

      return this.http.get<GetProductsResponse>(this.url +
        "/product/findbyseller?seller=" +
        this.user.getUsername() +
        "&page=1").pipe(
          tap(x => toReturn = x.products),
          tap(x => console.log('toReturn', x)),
          switchMap(x => this.generateProductsRequests(x.total)),
          switchMap(x => {
            if(x.length === 0) {
              return of(toReturn)
            } else {
              var res: Product[] = []
              res = toReturn.concat(x)
              return of(res)
            }
            
            }),
          tap(x => console.log('FINAL', x))
      );

    } else {
      return new Observable<Product[]>(observer => {
        observer.next(productsResponse.products);
        observer.complete();
      })
    }
  }

  generateProductsRequests(total: number): Observable<Product[]> {
    var req = []
    if (total === 1) return of([])
    for(let i = 2; i <= total; i++) {
      req.push(
        this.http.get<GetProductsResponse>(this.url +
          "/product/findbyseller?seller=" +
          this.user.getUsername() +
          "&page=" + i).pipe(
            map(x => x.products)
          )
      );
    }

    return forkJoin(req).pipe(
      map(page => {
        var toObservable: Product[] = []
        console.log('partial', toObservable)
        page.forEach(product => product.forEach)
        return toObservable
      })
    )

  }

  deleteProduct(id: string) {
    if(this.serviceMode == 1) {
      return this.http.delete<ProductResponse>(
        this.url + "/product/"+id,
        {headers: this.user.getHeader()});
    } else {
      return new Observable<ProductResponse>(observer => {
        observer.next(productResponse);
        observer.complete();
      })
    }
  }


}
