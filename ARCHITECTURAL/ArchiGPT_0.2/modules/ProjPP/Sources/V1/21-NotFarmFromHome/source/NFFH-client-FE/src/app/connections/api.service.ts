import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, switchMap, tap } from 'rxjs';
import { OrderDTO, OrderPage, OrderPartDTO } from '../model/order';
import { UserService } from '../user.service';
import { revertString } from '../utils/stringmakeup';
import { Areas, AreasItemResponse, BaseStore, Cart, LoginRequest, LoginResponse, MyOrders, OrderResponse, Product, ProductAvailability, ProductResponse, RegistrationRequest, StoreProducts, StoresListResponse } from './connectionTypes';
import { cart } from './mockObjects/cart';
import { myOrders } from './mockObjects/personalpage';
import { store, storeProducts1, storeProducts2 } from './mockObjects/store';
import { areasList, storeslistresponse1, } from './mockObjects/storesList';

@Injectable({
  providedIn: 'root'
})
export class APIService {
  
  /** SERVICE MODE
   * 1 : API (connected to server)
   * 2: Mock
   */
  serviceMode = 1;
  url = "http://0.0.0.0:8080";

  constructor(
    private http: HttpClient, 
    private user: UserService) { }

  login(email: string, password: string) {
    if (this.serviceMode == 1) {
      const req: LoginRequest = {
        email: email,
        password: password
      }
      return this.http.post<LoginResponse>(this.url+'/client/login', req).pipe(
        tap(x => {
          if(x.success) {
            this.user.setUser(x.token, x.id, x.email, x.username)
          }
        })
      )
    }
    else {
      //this.user.setUser(loginResponse.token, loginResponse.name)
      return new Observable<LoginResponse>(observer => {
        observer.next({} as LoginResponse);
        observer.complete();
      })
    }
  }

  register(email: string, password: string, name: string) {
    if (this.serviceMode == 1) {
      const req: RegistrationRequest = {
        email: email,
        password: password,
        username: name
      }
      return this.http.post<LoginResponse>(this.url+'/client/signup', req).pipe(
        tap(x => this.user.setUser(x.token, x.id, x.email, x.username))
      )
    }
    else {
      //this.user.setUser(loginResponse.token, loginResponse.name)
      return new Observable<LoginResponse>(observer => {
        observer.next({} as LoginResponse);
        observer.complete();
      })
    }
  }

  getHomepageCards(): Observable<StoreProducts> {
    if(this.serviceMode == 1) {
      return this.http.get<StoresListResponse>(this.url + "/farmer/areas?area=Roma&page=1").pipe(
        switchMap(x => this.http.get<StoreProducts>(this.url + "/product/findbyseller?seller="+x.stores[0].username+"&page=1"))
      )
    }
    else {
      return new Observable<StoreProducts>(observer => {
        observer.next({} as StoreProducts);
        observer.complete();
      })
    }
  }

  getLocationsList(): Observable<Areas> {
    if(this.serviceMode == 1) {
      return this.http.get<AreasItemResponse[]>(this.url+'/area').pipe(
        switchMap(x => {
          var a: Areas = {areas: []}
          x.forEach(area => a.areas.push(area.areaName))
          return of(a)
        })
      )
    } else {
      return new Observable<Areas>(observer => {
        observer.next(areasList);
        observer.complete();
      })
    }
  }

  getStoresList(page: number, area: string) {
    if(this.serviceMode == 1) {
      return this.http.get<StoresListResponse>(this.url+'/farmer/areas?area='+area+'&page='+page);
    }
    else {
      return new Observable<StoresListResponse>(observer => {
        if(page === 1) observer.next(storeslistresponse1);
        observer.complete();
      })
    }
  }

  getStore(name: string) {
    if(this.serviceMode == 1) {
      var temp = revertString(name)
      return this.http.get<BaseStore>(this.url+'/farmerLight/'+temp);
    } else {
      return new Observable<BaseStore>(observer => {
        observer.next(store);
        observer.complete();
      })
    }
  }

  getProducts(username: string, page: number) {
    if(this.serviceMode == 1) {
      var temp = revertString(username)
      return this.http.get<StoreProducts>(this.url+'/product/findbyseller?seller='+temp+'&page='+page);
    } else {
      return new Observable<StoreProducts>(observer => {
        if(page === 1) observer.next(storeProducts1);
        if(page === 2) observer.next(storeProducts2);
        observer.complete();
      })
    }
  }

  updateCart(cart: Cart) {
    if(this.serviceMode == 1) {
      return this.http.post(this.url+'/updatecart', cart);
    } else {
      return new Observable();
    }
  }

  getCart() {
    if(this.serviceMode == 1) {
      return this.http.get<Cart>(this.url+'/cart');
    } else {
      return new Observable<Cart>(observer => {
        observer.next(cart);
        observer.complete();
      })
    }
  }

  checkAvailability(product: Product): Observable<ProductAvailability> {
    if(this.serviceMode == 1) {
      return this.http.get<ProductResponse>(this.url+'/product/'+product.id).pipe(
        switchMap(x => {

          return of({

            available : x.product.availability,
            product : x.product
          })
        })
      )
    }else {
      return new Observable<ProductAvailability>(observer => {
        observer.next({ product: product, available: true });
        observer.complete();
      })
    }
  }

  completeOrder(order: OrderPage): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(
      this.url +'/order', 
      this.fromOrderPagetoOrderDTO(order))
 
  }

  fromOrderPagetoOrderDTO(order: OrderPage): OrderDTO {
    var parts: OrderPartDTO[] = []
    order.parts.forEach(x => parts.push({
      seller: parseInt(x.seller.id),
      productList: x.total.toString()
    }))
    console.log(this.user.getId(), 'penn')
    return {
      client: parseInt(this.user.getId()),
      commission: order.commission,
      pickup: order.pickup.toString(),
      accepted: true,
      total: order.total,
      orderPart: parts
    }
  }

  getOrders() {
    if(this.serviceMode == 1) {
      return this.http.get<MyOrders>(this.url+'/orders')
    } else {
      return new Observable<MyOrders>(observer => {
        observer.next(myOrders);
        observer.complete();
      })
    }
  }
}
