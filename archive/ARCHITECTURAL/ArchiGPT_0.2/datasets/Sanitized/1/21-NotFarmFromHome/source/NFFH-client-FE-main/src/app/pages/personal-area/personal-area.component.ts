import { Component } from '@angular/core';
import { Observable, concatMap, tap } from 'rxjs';
import { APIService } from 'src/app/connections/api.service';
import { BaseStore } from 'src/app/connections/connectionTypes';
import { Order } from 'src/app/model/order';

@Component({
  selector: 'app-personal-area',
  templateUrl: './personal-area.component.html',
  styleUrls: ['./personal-area.component.css']
})
export class PersonalAreaComponent {

  orders: Order[] = [];
  sellers: BaseStore[] = [];

  constructor(
    private api: APIService
  ) {
    this.api.getOrders().pipe(
      tap(res => this.orders = res.orders),
      concatMap(x => this.getSellersIds())
    ).subscribe(() => {
      this.orders.forEach(order => {
        order.products.forEach(prod => {
          var seller = this.sellers.find(store => store.id === prod.product.seller)
          prod.product.seller = seller?.username || '';
        })
      })
    })

  }

  getSellersIds(): Observable<BaseStore>[] {
    var ids: string[] = [];
    this.orders.forEach(order => {
      order.products.forEach(prod => {
        if(!ids.includes(prod.product.seller)) ids.push(prod.product.seller);
      })
    });

    var sub: Observable<BaseStore>[] = [];
    ids.forEach(id => sub.push(this.api.getStore(id).pipe(
      tap(res => this.sellers.push(res))
    )));
    return sub;
  }

}
