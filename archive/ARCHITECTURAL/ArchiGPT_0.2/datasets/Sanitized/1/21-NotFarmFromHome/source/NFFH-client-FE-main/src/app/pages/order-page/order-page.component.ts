import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, forkJoin, tap } from 'rxjs';
import { CartService } from 'src/app/cart.service';
import { APIService } from 'src/app/connections/api.service';
import { BaseStore, OrderPart } from 'src/app/connections/connectionTypes';
import { CartProduct } from 'src/app/model/cart';
import { OrderPage, OrderPartPage } from 'src/app/model/order';
import { modifyString } from 'src/app/utils/stringmakeup';

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.css']
})
export class OrderPageComponent {

  order: OrderPage = {} as OrderPage
  datepicker: FormControl = new FormControl();
  disabled = false;
  startDate = new Date();
  endDate = new Date();

  completed = false;
  error = false;

  constructor(
    private api: APIService,
    private cartService: CartService
  ) {
    var cart = this.cartService.getCart();

  
    this.order = this.generateOrderPage(cart);
    this.endDate.setDate(this.endDate.getDate() + 2);
    this.datepicker.valueChanges.subscribe(x => {
      console.log(x)
    })
    console.log('order', this.order)
  }

  confirmOrder() {

    this.api.completeOrder(this.order).subscribe(res => {
      if (res.success) {
        this.completed = true;
      } else {
        this.error = true;
      }
    })

  }

  fromOrderParttoOrderPartPage(part: OrderPart): OrderPartPage {
    var seller: BaseStore = {} as BaseStore;
    this.api.getStore(part.seller).subscribe(x => seller = x);
    return {
      seller: seller,
      total: part.total
    }
  }

  fromOrderPartPagetoOrderPart(part: OrderPartPage): OrderPart {
    return {
      seller: part.seller.id,
      total: part.total
    }
  }

  generateOrderPage(cart: CartProduct[]): OrderPage {
    
    var parts: OrderPartPage[] = []
    var sellers: string[] = []
    cart.forEach(item => {
      if(!sellers.find(i => i === item.product.seller)) sellers.push(item.product.seller)
    })

    var req: Observable<BaseStore>[] = []
    sellers.forEach(seller => req.push(this.api.getStore(seller).pipe(
      tap(s => {
        var temp = s
        temp.username = modifyString(s.username)
        parts.push({
        seller: temp,
        total: 0
      })})
    )))
    forkJoin(req).subscribe(x => cart.forEach(item => {
      var tempUsername = modifyString(item.product.seller)
      var temp = parts.find(part => part.seller.username === tempUsername)
      var total = parts[parts.indexOf(temp || {} as OrderPartPage)].total
      console.log(item)
      total = total + item.product.price * item.quantity
      parts[parts.indexOf(temp || {} as OrderPartPage)].total = total
    }))

    console.log(parts)
    //generate numbers
    const totalNoCommissions = parts.reduce((accumulator, currentValue) => accumulator + currentValue.total, 0)
    const commission = totalNoCommissions * 0.05;
    const total = totalNoCommissions + commission;
    console.log(totalNoCommissions, commission) 
    return {
      parts: parts,
      commission: commission,
      total: total,
      pickup: new Date(),
      completed: false
    }
  }
}
